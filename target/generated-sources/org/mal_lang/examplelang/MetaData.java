package org.mal_lang.examplelang;

import java.lang.String;
import java.util.HashMap;
import java.util.Map;

public final class MetaData {
  public static final String MAL_VERSION = "0.1.0-SNAPSHOT";

  public static final String ID = "org.mal-lang.examplelang";

  public static final String VERSION = "1.0.0";

  public static Map<String, String> DATA;

  static {
    DATA = new HashMap<>();
    DATA.put("id", "org.mal-lang.examplelang");
    DATA.put("version", "1.0.0");
  }
}
