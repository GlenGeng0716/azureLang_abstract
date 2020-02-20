package org.mal_lang.examplelang.test;

import core.Asset;
import core.AttackStep;
import core.AttackStepMin;
import java.util.HashSet;
import java.util.Set;

public class User extends Asset {
  public AttemptPhishing attemptPhishing;

  public Phish phish;

  public Set<Password> passwords = new HashSet<>();

  public User(String name) {
    super(name);
    assetClassName = "User";
    AttackStep.allAttackSteps.remove(attemptPhishing);
    attemptPhishing = new AttemptPhishing(name);
    AttackStep.allAttackSteps.remove(phish);
    phish = new Phish(name);
  }

  public User() {
    this("Anonymous");
  }

  public void addPasswords(Password passwords) {
    this.passwords.add(passwords);
    passwords.user = this;
  }

  @Override
  public String getAssociatedAssetClassName(String field) {
    if (field.equals("passwords")) {
      return Password.class.getName();
    }
    return "";
  }

  @Override
  public Set<Asset> getAssociatedAssets(String field) {
    Set<Asset> assets = new HashSet<>();
    if (field.equals("passwords")) {
      assets.addAll(passwords);
    }
    return assets;
  }

  @Override
  public Set<Asset> getAllAssociatedAssets() {
    Set<Asset> assets = new HashSet<>();
    assets.addAll(passwords);
    return assets;
  }

  public class AttemptPhishing extends AttackStepMin {
    public AttemptPhishing(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      phish.updateTtc(this, ttc, attackSteps);
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("User.attemptPhishing");
    }
  }

  public class Phish extends AttackStepMin {
    public Phish(String name) {
      super(name);
    }

    @Override
    public void updateChildren(Set<AttackStep> attackSteps) {
      for (Password _0 : passwords) {
        _0.obtain.updateTtc(this, ttc, attackSteps);
      }
    }

    @Override
    public void setExpectedParents() {
      super.setExpectedParents();
      addExpectedParent(attemptPhishing);
    }

    @Override
    public double localTtc() {
      return ttcHashMap.get("User.phish");
    }
  }
}
