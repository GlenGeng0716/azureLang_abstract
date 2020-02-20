package org.mal_lang.examplelang;

import com.foreseeti.corelib.Link;
import java.lang.Override;
import java.lang.String;

public enum AutoLangLink implements Link {
  hosts_networks("NetworkAccess"),

  passwords_host("Credentials"),

  passwords_user("Credentials");

  private final String name;

  AutoLangLink(String name) {
    this.name = name;
  }

  @Override
  public String getName() {
    return this.name;
  }
}
