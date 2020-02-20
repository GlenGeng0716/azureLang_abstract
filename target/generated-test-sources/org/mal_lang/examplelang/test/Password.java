package org.mal_lang.examplelang.test;

import core.Asset;
import core.AttackStep;
import core.AttackStepMin;
import java.util.HashSet;
import java.util.Set;

public class Password extends Asset {
  public Obtain obtain;

  public Host host = null;

  public User user = null;

  public Password(String name) {
    super(name);
    assetClassName = "Password";
    AttackStep.allAttackSteps.remove(obtain);
    obtain = new Obtain(name);
  }

  public Password() {
    this("Anonymous");
  }

  public void addHost(Host host) {
    this.host = host;
    host.passwords.add(this);
  }

  public void addUser(User user) {
    this.user = user;
    user.passwords.add(this);
  }

  @Override
  public String getAssociatedAssetClassName(String field) {
    if (field.equals("host")) {
      return Host.class.getName();
    } else if (field.equals("user")) {
      return User.class.getName();
    }
    return "";
  }

  @Override
  public Set<Asset> getAssociatedAssets(String field) {
    Set<Asset> assets = new HashSet<>();
    if (field.equals("host")) {
      if (host != null) {
        assets.add(host);
      }
    } else if (field.equals("user")) {
      if (user != null) {
        assets.add(user);
      }
    }
    return assets;
  }

  @Override
  public Set<Asset> getAllAssociatedAssets() {
    Set<Asset> assets = new HashSet<>();
    if (host != null) {
      assets.add(host);
    }
    if (user != null) {
      assets.add(user);
    }
    return assets;
  }

  public class Obtain extends AttackStepMin {
    public Obtain(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      if (host != null) {
        host.authenticate.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      if (user != null) {
        addExpectedParent(user.phish);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("Password.obtain");
    }
  }
}
