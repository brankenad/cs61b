package bearmaps.proj2c.server.handler.impl;

import bearmaps.proj2c.AugmentedStreetMapGraph;
import bearmaps.proj2c.server.handler.APIRouteHandler;
import spark.Request;
import spark.Response;
import bearmaps.proj2c.utils.Constants;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static bearmaps.proj2c.utils.Constants.*;

/**
 * Handles requests from the web browser for map images. These images
 * will be rastered into one large image to be displayed to the user.
 * @author rahul, Josh Hug, _________
 */
public class RasterAPIHandler extends APIRouteHandler<Map<String, Double>, Map<String, Object>> {

    /**
     * Each raster request to the server will have the following parameters
     * as keys in the params map accessible by,
     * i.e., params.get("ullat") inside RasterAPIHandler.processRequest(). <br>
     * ullat : upper left corner latitude, <br> ullon : upper left corner longitude, <br>
     * lrlat : lower right corner latitude,<br> lrlon : lower right corner longitude <br>
     * w : user viewport window width in pixels,<br> h : user viewport height in pixels.
     **/
    private static final String[] REQUIRED_RASTER_REQUEST_PARAMS = {"ullat", "ullon", "lrlat",
            "lrlon", "w", "h"};

    /**
     * The result of rastering must be a map containing all of the
     * fields listed in the comments for RasterAPIHandler.processRequest.
     **/
    private static final String[] REQUIRED_RASTER_RESULT_PARAMS = {"render_grid", "raster_ul_lon",
            "raster_ul_lat", "raster_lr_lon", "raster_lr_lat", "depth", "query_success"};


    @Override
    protected Map<String, Double> parseRequestParams(Request request) {
        return getRequestParams(request, REQUIRED_RASTER_REQUEST_PARAMS);
    }

    private void isSuccessful(Map<String, Double> request, Map<String, Object> result) {
        if (request.get("ullon") < ROOT_ULLON) {
            request.put("ullon", ROOT_ULLON);
            result.put("query_success", false);
            return;
        }
        if (request.get("ullat") > ROOT_ULLAT) {
            request.put("ullat", ROOT_ULLAT);
            result.put("query_success", false);
            return;
        }
        if (request.get("lrlon") > ROOT_LRLON) {
            request.put("lrlon", ROOT_LRLON);
            result.put("query_success", false);
            return;
        }
        if (request.get("lrlat") < ROOT_LRLAT) {
            request.put("lrlat", ROOT_LRLAT);
            result.put("query_success", false);
            return;
        }
        result.put("query_success", true);
    }

    private class RasterInit {
        private double ullon;
        private double ullat;
        private int x;
        private int y;
        private double LonDPI;
        private double LatDPI;

        RasterInit(double ullon, double ullat, int x , int y, int depth) {
            this.LonDPI = (ROOT_LRLON - ROOT_ULLON) / (Math.pow(2, depth));
            this.LatDPI = (ROOT_ULLAT - ROOT_LRLAT) / (Math.pow(2, depth));
            this.ullon = ullon + LonDPI;
            this.ullat = ullat - LatDPI;
            this.x = x;
            this.y = y;

        }
    }

    private void setDepth(Map<String, Object> result, double startlon, double endlon, double w) {
        int depth = 0;
        double theoLonDPP = (ROOT_LRLON - ROOT_ULLON) / TILE_SIZE;
        double realLonDPP = (endlon - startlon) / w;
        while (theoLonDPP > realLonDPP) {
            theoLonDPP /= 2.0;
            depth += 1;
            if (depth == 7) {
                break;
            }
        }
        result.put("depth", depth);
    }

    private Map<String, Integer> setRaster(RasterInit ri, double lon, double lat, Map<String, Object> result, String str) {
        Map<String, Integer> rasterPosi = new HashMap<>();
        double theoEndLon = ri.ullon;
        double theoEndLat = ri.ullat;
        int endPosiX = ri.x;
        int endPoisY = ri.y;

        while (theoEndLon < lon || theoEndLat > lat) {
            if (theoEndLon < lon) {
               endPosiX += 1;
               theoEndLon += ri.LonDPI;
            }
            if (theoEndLat > lat) {
                endPoisY += 1;
                theoEndLat -= ri.LatDPI;
            }
        }

        rasterPosi.put("X", endPosiX);
        rasterPosi.put("Y", endPoisY);
        if (str.equals("UL")) {
            result.put("raster_ul_lon", theoEndLon - ri.LonDPI);
            result.put("raster_ul_lat", theoEndLat + ri.LatDPI);
        } else if (str.equals("LR")) {
            result.put("raster_lr_lon", theoEndLon);
            result.put("raster_lr_lat", theoEndLat);
        }

        return rasterPosi;
    }

    private void setRendGird(int depth, int startRow, int endRow, int startCol, int endCol, Map<String, Object> result) {
        int row = endRow - startRow + 1;
        int column = endCol - startCol + 1;
        String[][] arrGird = new String[row][column];
        for (int i = 0; i < row; i += 1) {
            for (int j = 0; j < column; j += 1) {
                arrGird[i][j] = String.format("d%s_x%s_y%s.png", depth, j + startCol, i + startRow);
            }
        }

        result.put("render_grid", arrGird);
    }
    /**
     * Takes a user query and finds the grid of images that best matches the query. These
     * images will be combined into one big image (rastered) by the front end. <br>
     *
     *     The grid of images must obey the following properties, where image in the
     *     grid is referred to as a "tile".
     *     <ul>
     *         <li>The tiles collected must cover the most longitudinal distance per pixel
     *         (LonDPP) possible, while still covering less than or equal to the amount of
     *         longitudinal distance per pixel in the query box for the user viewport size. </li>
     *         <li>Contains all tiles that intersect the query bounding box that fulfill the
     *         above condition.</li>
     *         <li>The tiles must be arranged in-order to reconstruct the full image.</li>
     *     </ul>
     *
     * @param requestParams Map of the HTTP GET request's query parameters - the query box and
     *               the user viewport width and height.
     *
     * @param response : Not used by this function. You may ignore.
     * @return A map of results for the front end as specified: <br>
     * "render_grid"   : String[][], the files to display. <br>
     * "raster_ul_lon" : Number, the bounding upper left longitude of the rastered image. <br>
     * "raster_ul_lat" : Number, the bounding upper left latitude of the rastered image. <br>
     * "raster_lr_lon" : Number, the bounding lower right longitude of the rastered image. <br>
     * "raster_lr_lat" : Number, the bounding lower right latitude of the rastered image. <br>
     * "depth"         : Number, the depth of the nodes of the rastered image;
     *                    can also be interpreted as the length of the numbers in the image
     *                    string. <br>
     * "query_success" : Boolean, whether the query was able to successfully complete; don't
     *                    forget to set this to true on success! <br>
     */
    @Override
    public Map<String, Object> processRequest(Map<String, Double> requestParams, Response response) {
        //System.out.println("yo, wanna know the parameters given by the web browser? They are:");
        //System.out.println(requestParams);
        Map<String, Object> results = new HashMap<>();
        /**
        System.out.println("Since you haven't implemented RasterAPIHandler.processRequest, nothing is displayed in "
                + "your browser.");
        */
        isSuccessful(requestParams, results);

        double ullon = requestParams.get("ullon");
        double ullat = requestParams.get("ullat");
        double lrlon = requestParams.get("lrlon");
        double lrlat = requestParams.get("lrlat");
        double width = requestParams.get("w");

        setDepth(results, ullon, lrlon, width);
        int depth = (int) results.get("depth");

        RasterInit startRaster = new RasterInit(ROOT_ULLON, ROOT_ULLAT, 0, 0, depth);
        Map<String, Integer> startRasterPosi = setRaster(startRaster, ullon, ullat, results, "UL");
        int startRasterPosiX = startRasterPosi.get("X");
        int startRasterPosiY = startRasterPosi.get("Y");
        double startRasterUlLon = (double) results.get("raster_ul_lon");
        double startRasterUlLat = (double) results.get("raster_ul_lat");


        RasterInit endRaster = new RasterInit(startRasterUlLon, startRasterUlLat, startRasterPosiX, startRasterPosiY, depth);
        Map<String, Integer> endRasterPosi = setRaster(endRaster, lrlon, lrlat, results, "LR");
        int endRasterPosiX = endRasterPosi.get("X");
        int endRasterPosiY = endRasterPosi.get("Y");

        setRendGird(depth, startRasterPosiY, endRasterPosiY, startRasterPosiX, endRasterPosiX, results);

        return results;
    }

    @Override
    protected Object buildJsonResponse(Map<String, Object> result) {
        boolean rasterSuccess = validateRasteredImgParams(result);

        if (rasterSuccess) {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            writeImagesToOutputStream(result, os);
            String encodedImage = Base64.getEncoder().encodeToString(os.toByteArray());
            result.put("b64_encoded_image_data", encodedImage);
        }
        return super.buildJsonResponse(result);
    }

    private Map<String, Object> queryFail() {
        Map<String, Object> results = new HashMap<>();
        results.put("render_grid", null);
        results.put("raster_ul_lon", 0);
        results.put("raster_ul_lat", 0);
        results.put("raster_lr_lon", 0);
        results.put("raster_lr_lat", 0);
        results.put("depth", 0);
        results.put("query_success", false);
        return results;
    }

    /**
     * Validates that Rasterer has returned a result that can be rendered.
     * @param rip : Parameters provided by the rasterer
     */
    private boolean validateRasteredImgParams(Map<String, Object> rip) {
        for (String p : REQUIRED_RASTER_RESULT_PARAMS) {
            if (!rip.containsKey(p)) {
                System.out.println("Your rastering result is missing the " + p + " field.");
                return false;
            }
        }
        if (rip.containsKey("query_success")) {
            boolean success = (boolean) rip.get("query_success");
            if (!success) {
                System.out.println("query_success was reported as a failure");
                return false;
            }
        }
        return true;
    }

    /**
     * Writes the images corresponding to rasteredImgParams to the output stream.
     * In Spring 2016, students had to do this on their own, but in 2017,
     * we made this into provided code since it was just a bit too low level.
     */
    private  void writeImagesToOutputStream(Map<String, Object> rasteredImageParams,
                                                  ByteArrayOutputStream os) {
        String[][] renderGrid = (String[][]) rasteredImageParams.get("render_grid");
        int numVertTiles = renderGrid.length;
        int numHorizTiles = renderGrid[0].length;

        BufferedImage img = new BufferedImage(numHorizTiles * Constants.TILE_SIZE,
                numVertTiles * Constants.TILE_SIZE, BufferedImage.TYPE_INT_RGB);
        Graphics graphic = img.getGraphics();
        int x = 0, y = 0;

        for (int r = 0; r < numVertTiles; r += 1) {
            for (int c = 0; c < numHorizTiles; c += 1) {
                graphic.drawImage(getImage(Constants.IMG_ROOT + renderGrid[r][c]), x, y, null);
                x += Constants.TILE_SIZE;
                if (x >= img.getWidth()) {
                    x = 0;
                    y += Constants.TILE_SIZE;
                }
            }
        }

        /* If there is a route, draw it. */
        double ullon = (double) rasteredImageParams.get("raster_ul_lon"); //tiles.get(0).ulp;
        double ullat = (double) rasteredImageParams.get("raster_ul_lat"); //tiles.get(0).ulp;
        double lrlon = (double) rasteredImageParams.get("raster_lr_lon"); //tiles.get(0).ulp;
        double lrlat = (double) rasteredImageParams.get("raster_lr_lat"); //tiles.get(0).ulp;

        final double wdpp = (lrlon - ullon) / img.getWidth();
        final double hdpp = (ullat - lrlat) / img.getHeight();
        AugmentedStreetMapGraph graph = SEMANTIC_STREET_GRAPH;
        List<Long> route = ROUTE_LIST;

        if (route != null && !route.isEmpty()) {
            Graphics2D g2d = (Graphics2D) graphic;
            g2d.setColor(Constants.ROUTE_STROKE_COLOR);
            g2d.setStroke(new BasicStroke(Constants.ROUTE_STROKE_WIDTH_PX,
                    BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            route.stream().reduce((v, w) -> {
                g2d.drawLine((int) ((graph.lon(v) - ullon) * (1 / wdpp)),
                        (int) ((ullat - graph.lat(v)) * (1 / hdpp)),
                        (int) ((graph.lon(w) - ullon) * (1 / wdpp)),
                        (int) ((ullat - graph.lat(w)) * (1 / hdpp)));
                return w;
            });
        }

        rasteredImageParams.put("raster_width", img.getWidth());
        rasteredImageParams.put("raster_height", img.getHeight());

        try {
            ImageIO.write(img, "png", os);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private BufferedImage getImage(String imgPath) {
        BufferedImage tileImg = null;
        if (tileImg == null) {
            try {
                File in = new File(imgPath);
                tileImg = ImageIO.read(in);
            } catch (IOException | NullPointerException e) {
                e.printStackTrace();
            }
        }
        return tileImg;
    }
}
