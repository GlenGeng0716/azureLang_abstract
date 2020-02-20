package org.mal_lang.examplelang.test;

import core.Asset;
import core.AttackStep;
import core.AttackStepMax;
import core.AttackStepMin;
import java.util.HashSet;
import java.util.Set;

public class Host extends Asset {
  public Connect connect;

  public Authenticate authenticate;

  public GuessPassword guessPassword;

  public GuessedPassword guessedPassword;

  public Access access;

  public Set<Network> networks = new HashSet<>();

  public Set<Password> passwords = new HashSet<>();

  public Host(String name) {
    super(name);
    assetClassName = "Host";
    AttackStep.allAttackSteps.remove(connect);
    connect = new Connect(name);
    AttackStep.allAttackSteps.remove(authenticate);
    authenticate = new Authenticate(name);
    AttackStep.allAttackSteps.remove(guessPassword);
    guessPassword = new GuessPassword(name);
    AttackStep.allAttackSteps.remove(guessedPassword);
    guessedPassword = new GuessedPassword(name);
    AttackStep.allAttackSteps.remove(access);
    access = new Access(name);
  }

  public Host() {
    this("Anonymous");
  }

  public void addNetworks(Network networks) {
    this.networks.add(networks);
    networks.hosts.add(this);
  }

  public void addPasswords(Password passwords) {
    this.passwords.add(passwords);
    passwords.host = this;
  }

  @Override
  public String getAssociatedAssetClassName(String field) {
    if (field.equals("networks")) {
      return Network.class.getName();
    } else if (field.equals("passwords")) {
      return Password.class.getName();
    }
    return "";
  }

  @Override
  public Set<Asset> getAssociatedAssets(String field) {
    Set<Asset> assets = new HashSet<>();
    if (field.equals("networks")) {
      assets.addAll(networks);
    } else if (field.equals("passwords")) {
      assets.addAll(passwords);
    }
    return assets;
  }

  @Override
  public Set<Asset> getAllAssociatedAssets() {
    Set<Asset> assets = new HashSet<>();
    assets.addAll(networks);
    assets.addAll(passwords);
    return assets;
  }

  public class Connect extends AttackStepMin {
    public Connect(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      access.updateTtc(this, ttc, attackSteps);
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      for (Network _0 : networks) {
        addExpectedParent(_0.access);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("Host.connect");
    }
  }

  public class Authenticate extends AttackStepMin {
    public Authenticate(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      access.updateTtc(this, ttc, attackSteps);
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      addExpectedParent(guessedPassword);
      for (Password _0 : passwords) {
        addExpectedParent(_0.obtain);
      }
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("Host.authenticate");
    }
  }

  public class GuessPassword extends AttackStepMin {
    public GuessPassword(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      guessedPassword.updateTtc(this, ttc, attackSteps);
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("Host.guessPassword");
    }
  }

  public class GuessedPassword extends AttackStepMin {
    public GuessedPassword(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      authenticate.updateTtc(this, ttc, attackSteps);
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      addExpectedParent(guessPassword);
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("Host.guessedPassword");
    }
  }

  public class Access extends AttackStepMax {
    public Access(String name) {
      super(name);
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      addExpectedParent(connect);
      addExpectedParent(authenticate);
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("Host.access");
    }
  }
}
