package za.co.santongrocery.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections.IteratorUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import za.co.santongrocery.api.exception.BusinessException;
import za.co.santongrocery.api.exception.Errors;


public final class JSONUtil {
    private JSONUtil() {
    }

    private static JSONObject getJSONObjectFromPath(JSONObject jsonObject, String[] path, int length) throws JSONException {
        JSONObject current = jsonObject;
        int loop = 0;
        while (true) {
            if (loop == length) {
                return jsonObject;
            } else if (loop == length - 1) {
                return current.getJSONObject(path[loop]);
            } else {
                current = current.getJSONObject(path[loop]);
                loop += 1;
            }
        }
    }

    public static boolean hasElement(JSONObject jsonObject, String periodDelimitedPath) {
        try {
            InternalJSONNode internalJSONNode = new InternalJSONNode(jsonObject, periodDelimitedPath);
            return internalJSONNode.getLastJSONNode().has(internalJSONNode.getLastPathElement());
        } catch (JSONException e) {
            return false;
        }
    }

    public static int getInt(JSONObject jsonObject, String periodDelimitedPath) throws JSONException {
        InternalJSONNode internalJSONNode = new InternalJSONNode(jsonObject, periodDelimitedPath);
        return internalJSONNode.getLastJSONNode().getInt(internalJSONNode.getLastPathElement());
    }

    public static JSONObject getJSONObject(JSONObject jsonObject, String periodDelimitedPath) throws JSONException {
        InternalJSONNode internalJSONNode = new InternalJSONNode(jsonObject, periodDelimitedPath);
        return internalJSONNode.getLastJSONNode().getJSONObject(internalJSONNode.getLastPathElement());
    }

    public static JSONArray getJSONArray(JSONObject jsonObject, String periodDelimitedPath) throws JSONException {
        InternalJSONNode internalJSONNode = new InternalJSONNode(jsonObject, periodDelimitedPath);
        return internalJSONNode.getLastJSONNode().getJSONArray(internalJSONNode.getLastPathElement());
    }

    public static String getString(JSONObject jsonObject, String periodDelimitedPath) throws JSONException {
        InternalJSONNode internalJSONNode = new InternalJSONNode(jsonObject, periodDelimitedPath);
        return internalJSONNode.getLastJSONNode().getString(internalJSONNode.getLastPathElement());
    }

    public static String getString(JSONObject jsonObject, String periodDelimitedPath, String nullDefault) {
        try {
            InternalJSONNode internalJSONNode = new InternalJSONNode(jsonObject, periodDelimitedPath);
            return internalJSONNode.getLastJSONNode().getString(internalJSONNode.getLastPathElement());
        } catch (JSONException ignored) {
            return nullDefault;
        }
    }

    public static void setString(JSONObject jsonObject, String periodDelimitedPath, String value) throws JSONException {
        InternalJSONNode internalJSONNode = new InternalJSONNode(jsonObject, periodDelimitedPath);
        internalJSONNode.getLastJSONNode().put(internalJSONNode.getLastPathElement(), value);
    }

    private static void removeEmptyElements(JSONArray jsonArray) throws JSONException {
        for (int i = 0; i < jsonArray.length(); i += 1) {
            Object arrayElement = jsonArray.get(i);

            if (arrayElement instanceof JSONObject) {
                removeEmptyElements((JSONObject) arrayElement);
            } else if (arrayElement instanceof JSONArray) {
                removeEmptyElements((JSONArray) arrayElement);
            }
        }
    }

    public static void removeEmptyElements(JSONObject jsonObject) throws JSONException {
        for (Object key : IteratorUtils.toArray(jsonObject.keys())) {
            if (key instanceof String) {
                Object value = jsonObject.get((String) key);
                if (value instanceof String) {
                    if (StringUtils.isEmpty((String) value)) {
                        jsonObject.remove((String) key);
                    }
                } else if (value instanceof JSONObject) {
                    removeEmptyElements((JSONObject) value);
                } else if (value instanceof JSONArray) {
                    removeEmptyElements((JSONArray) value);
                }
            }
        }
    }

    static class InternalJSONNode {
        private JSONObject lastJSONNode;
        private String lastPathElement;

        InternalJSONNode(JSONObject jsonObject, String periodDelimitedPath) throws JSONException {
            String[] path = periodDelimitedPath.split("\\.");
            int pathSize = path.length;

            this.lastJSONNode = getJSONObjectFromPath(jsonObject, path, pathSize - 1);
            this.lastPathElement = path[pathSize - 1];
        }

        JSONObject getLastJSONNode() {
            return lastJSONNode;
        }

        String getLastPathElement() {
            return lastPathElement;
        }
    }

    public static String formatDate(java.util.Date date) {
        return DateFormatUtils.ISO_DATE_FORMAT.format(date);
    }

    public static String formatDate(java.util.Date date, String nullResult) {
        if (date == null) {
            return nullResult;
        } else {
            return formatDate(date);
        }
    }

    public static String formatDateTime(java.util.Date date) {
        return DateFormatUtils.ISO_DATETIME_FORMAT.format(date);
    }

    public static String formatDateTime(java.util.Date date, String nullResult) {
        if (date == null) {
            return nullResult;
        } else {
            return formatDateTime(date);
        }
    }

    public static JSONObject getJSONObject(Object object) throws BusinessException {
        ObjectMapper mapper = new ObjectMapper();
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(mapper.writeValueAsString(object));
            JSONUtil.removeEmptyElements(jsonObject);
        } catch (JsonProcessingException e) {
            throw new BusinessException(Errors.JSON_PROCESSING_ERROR);
        } catch (JSONException e) {
            throw new BusinessException(Errors.JSON_ERROR);
        }
        return jsonObject;
    }
}
