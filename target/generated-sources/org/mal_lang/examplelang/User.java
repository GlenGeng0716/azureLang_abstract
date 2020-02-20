package org.mal_lang.examplelang;

import com.foreseeti.corelib.AssociationManager;
import com.foreseeti.corelib.BaseSample;
import com.foreseeti.corelib.DefaultValue;
import com.foreseeti.corelib.FAnnotations.Association;
import com.foreseeti.corelib.FAnnotations.Category;
import com.foreseeti.corelib.FAnnotations.Display;
import com.foreseeti.corelib.FAnnotations.DisplayClass;
import com.foreseeti.corelib.FAnnotations.TypeName;
import com.foreseeti.corelib.FClass;
import com.foreseeti.corelib.ModelElement;
import com.foreseeti.corelib.math.FMath;
import com.foreseeti.corelib.util.FProbSet;
import com.foreseeti.simulator.AttackStep;
import com.foreseeti.simulator.AttackStepMax;
import com.foreseeti.simulator.AttackStepMin;
import com.foreseeti.simulator.ConcreteSample;
import com.foreseeti.simulator.Defense;
import com.foreseeti.simulator.MultiParentAsset;
import java.util.HashSet;
import java.util.Set;

@DisplayClass(
    category = Category.System
)
@TypeName(
    name = "User"
)
public class User extends MultiParentAsset {
  protected Set<AttackStep> attackSteps;

  protected Set<Defense> defenses;

  @Association(
      index = 1,
      name = "passwords"
  )
  public FProbSet<Password> passwords = new FProbSet<>();

  @Association(
      index = 2,
      name = "attemptPhishing"
  )
  @Display
  public AttemptPhishing attemptPhishing;

  @Association(
      index = 3,
      name = "phish"
  )
  @Display
  public Phish phish;

  public User() {
    this(DefaultValue.False);
  }

  public User(DefaultValue value) {
    initAttackSteps(value);
    initLists();
  }

  public User(User other) {
    super(other);
    attemptPhishing = new AttemptPhishing(other.attemptPhishing);
    phish = new Phish(other.phish);
    initLists();
  }

  public Set<Password> passwords(BaseSample sample) {
    return toSampleSet(this.passwords, sample);
  }

  @Override
  public void registerAssociations() {
    AssociationManager.addSupportedAssociationMultiple(this.getClass(), "passwords", Password.class, 0, 2147483647, AutoLangLink.passwords_user);
  }

  protected void initLists() {
    Set<AttackStep> attackSteps = new HashSet<>();
    attackSteps.add(attemptPhishing);
    attackSteps.add(phish);
    this.attackSteps = Set.copyOf(attackSteps);
    Set<Defense> defenses = new HashSet<>();
    this.defenses = Set.copyOf(defenses);
    fillElementMap();
  }

  protected void initAttackSteps(DefaultValue value) {
    this.attemptPhishing = new AttemptPhishing();
    this.phish = new Phish();
  }

  @Override
  public String getDescription() {
    return "";
  }

  @Override
  public Set<ModelElement> getTTCColoringElements() {
    Set<ModelElement> elements = new HashSet<>();
    return elements;
  }

  @Override
  public Set<AttackStep> getAttackSteps() {
    return this.attackSteps;
  }

  @Override
  public Set<Defense> getDefenses() {
    return this.defenses;
  }

  public static String getIconSVG() {
    return "data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iaXNvLTg4NTktMSI/Pg0KPCEtLSBHZW5lcmF0b3I6IEFkb2JlIElsbHVzdHJhdG9yIDE5LjAuMCwgU1ZHIEV4cG9ydCBQbHVnLUluIC4gU1ZHIFZlcnNpb246IDYuMDAgQnVpbGQgMCkgIC0tPg0KPHN2ZyB2ZXJzaW9uPSIxLjEiIGlkPSJDYXBhXzEiIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyIgeG1sbnM6eGxpbms9Imh0dHA6Ly93d3cudzMub3JnLzE5OTkveGxpbmsiIHg9IjBweCIgeT0iMHB4Ig0KCSB2aWV3Qm94PSIwIDAgNTEyIDUxMiIgc3R5bGU9ImVuYWJsZS1iYWNrZ3JvdW5kOm5ldyAwIDAgNTEyIDUxMjsiIHhtbDpzcGFjZT0icHJlc2VydmUiPg0KPGc+DQoJPGc+DQoJCTxwYXRoIGQ9Ik00MzcuMDIsMzMwLjk4Yy0yNy44ODMtMjcuODgyLTYxLjA3MS00OC41MjMtOTcuMjgxLTYxLjAxOEMzNzguNTIxLDI0My4yNTEsNDA0LDE5OC41NDgsNDA0LDE0OA0KCQkJQzQwNCw2Ni4zOTMsMzM3LjYwNywwLDI1NiwwUzEwOCw2Ni4zOTMsMTA4LDE0OGMwLDUwLjU0OCwyNS40NzksOTUuMjUxLDY0LjI2MiwxMjEuOTYyDQoJCQljLTM2LjIxLDEyLjQ5NS02OS4zOTgsMzMuMTM2LTk3LjI4MSw2MS4wMThDMjYuNjI5LDM3OS4zMzMsMCw0NDMuNjIsMCw1MTJoNDBjMC0xMTkuMTAzLDk2Ljg5Ny0yMTYsMjE2LTIxNnMyMTYsOTYuODk3LDIxNiwyMTYNCgkJCWg0MEM1MTIsNDQzLjYyLDQ4NS4zNzEsMzc5LjMzMyw0MzcuMDIsMzMwLjk4eiBNMjU2LDI1NmMtNTkuNTUxLDAtMTA4LTQ4LjQ0OC0xMDgtMTA4UzE5Ni40NDksNDAsMjU2LDQwDQoJCQljNTkuNTUxLDAsMTA4LDQ4LjQ0OCwxMDgsMTA4UzMxNS41NTEsMjU2LDI1NiwyNTZ6Ii8+DQoJPC9nPg0KPC9nPg0KPGc+DQo8L2c+DQo8Zz4NCjwvZz4NCjxnPg0KPC9nPg0KPGc+DQo8L2c+DQo8Zz4NCjwvZz4NCjxnPg0KPC9nPg0KPGc+DQo8L2c+DQo8Zz4NCjwvZz4NCjxnPg0KPC9nPg0KPGc+DQo8L2c+DQo8Zz4NCjwvZz4NCjxnPg0KPC9nPg0KPGc+DQo8L2c+DQo8Zz4NCjwvZz4NCjxnPg0KPC9nPg0KPC9zdmc+DQo=";
  }

  public static String getIconPNG() {
    return "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAADAAAAAwCAYAAABXAvmHAAADbElEQVR4Xs2ZS6hNYRiGv3JNMiDXM1IuMxRSRqdkJoaKyRkQBgyUZMKMITExUqcU5sqAIpKkKLmEySmlRMk1x/17tr2s7/v/dbL2Wv9aZz311m7/3/++776cf++1j0haVquOqa6pnqs+9sVt7mONmc6xVfVI9bukmGXPpDOkuiFxwbJiLx6TwgbVK4lLZXqputcXt8P1THjg1SprVZ8lLvNEtVeKn1XuY42ZcB9eeLbCYomf0S+qfaopZm4imGGWPdYDT7wb57L44Neq9W6iHOxhr/XCu1GGxQd+VW20AwPCXjys57AdSM1N8WGH/HIl8LCeZDQCf4S/JA8aU82wAxXBY0xyXzKKDoHacILYZ+qoX64FXtabrOSMig9Z45drgZf1Jis5VyUP+Kma5pdrgReemT9ZyXkseQDHX2rskUpWcuwDeBespQDPRh/ALfHv01l+uRZ4WW+yknNWfMgmv1wLvKw3WckZER9yxq3WAy/rPeJWEzFP9V3yEK60FrqJauCBV+ZLBlmNcEH8M3XOL1cCD+tJRmOsUv0QH7jHTQwGe60X3mQ0yknxobzk+91EOdhj35II78aZKX8vE20wuqRaauYmghlmw/144t0KS1TPJC4xrrqo2qFapprdF7e5jzVmwn144dkqXP7dkbjMoMKjlUvJkDmqg6pvEpcqK/bigVdrzFedluJfJaoKLzzxboypqiOq9xIXCPVG9VR1vS9uc184FwpvMshKCn+EdyUOzMRvPcdVm1Vz+3uKYI0ZZot+H8pEFplJ2CL+oz4TZ/h51bp8dGDYi0f4eYDIJLsWu6TY/IpquZmrC154hjlk06ESuyU2/KDaaYcSgzcZYS5dBmKbxN95XqhW2KGGIIMsm00XOpWClzN8Fh5Imq/OZSGLzPDV/+/bdrrqvsTP/AI71BJkhq8E3eg4IYclftQr3US7kB2+G+hYyJDEx2WTf7BloYPtREe6RpwSP8ix1hXCI5auDq5BP0k+wPnbxolTFrrYzyO6uuvmA2YR8enYNehkO9L5H+H3nCr/cWkaOtmOdO6xSPzv/nzJ6ir2CyCd6S7bzZ3oRDbdQehmu9I9On34uttV6Ga79k6j8Ihq7FexBNDNdu0d9fbjmqumrmOv7Ogub80dD/O5zkLHrC/d3e80t/O5zkLHrO/4H/sZ9TwuiOT3AAAAAElFTkSuQmCC";
  }

  @Deprecated
  public static String getIcon() {
    return getIconPNG();
  }

  public class LocalAttackStepMin extends AttackStepMin {
    LocalAttackStepMin() {
    }

    LocalAttackStepMin(LocalAttackStepMin other) {
      super(other);
    }

    @Override
    public FClass getContainerFClass() {
      return User.this;
    }
  }

  public class LocalAttackStepMax extends AttackStepMax {
    LocalAttackStepMax() {
    }

    LocalAttackStepMax(LocalAttackStepMax other) {
      super(other);
    }

    @Override
    public FClass getContainerFClass() {
      return User.this;
    }
  }

  public class LocalDefense extends Defense {
    LocalDefense() {
    }

    LocalDefense(LocalDefense other) {
      super(other);
    }

    LocalDefense(boolean other) {
      super(other);
    }

    @Override
    public FClass getContainerFClass() {
      return User.this;
    }
  }

  public class AttemptPhishing extends LocalAttackStepMin {
    public AttemptPhishing() {
    }

    public AttemptPhishing(AttemptPhishing other) {
      super(other);
    }

    @Override
    public Set<AttackStep> getAttackStepChildren() {
      Set<AttackStep> _0 = new HashSet<>();
      _0.add(phish);
      return _0;
    }

    public boolean isTrace() {
      return false;
    }
  }

  public class Phish extends LocalAttackStepMin {
    public Phish() {
    }

    public Phish(Phish other) {
      super(other);
    }

    @Override
    public double defaultLocalTtc(BaseSample sample, AttackStep caller) {
      return FMath.getExponentialDist(10.0).sample();
    }

    @Override
    public Set<AttackStep> getAttackStepChildren() {
      Set<AttackStep> _0 = new HashSet<>();
      for (Password _1 : passwords(null)) {
        _0.add(_1.obtain);
      }
      return _0;
    }

    @Override
    public void setExpectedParents(ConcreteSample sample) {
      super.setExpectedParents(sample);
      sample.addExpectedParent(this, attemptPhishing);
    }

    public boolean isTrace() {
      return false;
    }
  }
}
