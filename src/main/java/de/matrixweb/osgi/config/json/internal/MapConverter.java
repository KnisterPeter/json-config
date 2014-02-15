package de.matrixweb.osgi.config.json.internal;

import java.util.Collection;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author markusw
 */
public final class MapConverter {

  private MapConverter() {
  }

  /**
   * Converts the given {@link Map} to a {@link Dictionary}. The converter could
   * convert valid JSON to a key-value properties format consisting only of
   * simple datatyes. <br>
   * For exampel this JSON would be converted to the following key-value pairs:<br>
   * JSON:
   * 
   * <pre>
   * {
   *   "some": "test",
   *   "array": [1, 2, 3],
   *   "array2": [
   *     {"a":"b"},
   *     {"c":"d"}
   *   ],
   *   "object": {
   *     "key1": "value1",
   *     "key2": "value2"
   *   }
   * }
   * 
   * </pre>
   * 
   * <br>
   * Key-Value pairs:
   * 
   * <pre>
   * some=test
   * array.0=1
   * array.1=2
   * array.2=3
   * array2.0.a=b
   * array2.1.c=d
   * object.key1=value1
   * object.key2=value2
   * </pre>
   * 
   * @param map
   *          The {@link Map} to convert to {@link Dictionary}
   * @return Returns the converted {@link Dictionary}
   */
  public static Dictionary<String, Object> convert(final Map<String, Object> map) {
    final Dictionary<String, Object> dict = new Hashtable<String, Object>();
    convert(dict, "", map);
    return dict;
  }

  private static void convert(final Dictionary<String, Object> dict,
      final String base, final Map<String, Object> map) {
    for (final Entry<String, Object> entry : map.entrySet()) {
      convert(dict, base, entry.getKey(), entry.getValue());
    }
  }

  private static void convert(final Dictionary<String, Object> dict,
      final String base, final Collection<Object> collection) {
    int key = 0;
    final Iterator<Object> it = collection.iterator();
    while (it.hasNext()) {
      convert(dict, base, String.valueOf(key), it.next());
      key++;
    }
  }

  @SuppressWarnings("unchecked")
  private static void convert(final Dictionary<String, Object> dict,
      final String base, final String key, final Object value) {
    if (isSimpleType(value.getClass())) {
      dict.put(base + key, value);
    } else if (value instanceof Collection) {
      convert(dict, base + key + '.', (Collection<Object>) value);
    } else if (value instanceof Map) {
      convert(dict, base + key + '.', (Map<String, Object>) value);
    } else {
      throw new IllegalArgumentException("key:" + key + ":value:" + value);
    }
  }

  private static boolean isSimpleType(final Class<?> type) {
    return type == String.class || type == Integer.class || type == Long.class
        || type == Float.class || type == Double.class || type == Byte.class
        || type == Short.class || type == Character.class
        || type == Boolean.class;
  }

}
