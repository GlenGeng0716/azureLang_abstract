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
    name = "Host"
)
public class Host extends MultiParentAsset {
  protected Set<AttackStep> attackSteps;

  protected Set<Defense> defenses;

  @Association(
      index = 1,
      name = "networks"
  )
  public FProbSet<Network> networks = new FProbSet<>();

  @Association(
      index = 2,
      name = "passwords"
  )
  public FProbSet<Password> passwords = new FProbSet<>();

  @Association(
      index = 3,
      name = "connect"
  )
  @Display
  public Connect connect;

  @Association(
      index = 4,
      name = "authenticate"
  )
  @Display
  public Authenticate authenticate;

  @Association(
      index = 5,
      name = "guessPassword"
  )
  @Display
  public GuessPassword guessPassword;

  @Association(
      index = 6,
      name = "guessedPassword"
  )
  @Display
  public GuessedPassword guessedPassword;

  @Association(
      index = 7,
      name = "access"
  )
  @Display
  public Access access;

  public Host() {
    this(DefaultValue.False);
  }

  public Host(DefaultValue value) {
    initAttackSteps(value);
    initLists();
  }

  public Host(Host other) {
    super(other);
    connect = new Connect(other.connect);
    authenticate = new Authenticate(other.authenticate);
    guessPassword = new GuessPassword(other.guessPassword);
    guessedPassword = new GuessedPassword(other.guessedPassword);
    access = new Access(other.access);
    initLists();
  }

  public Set<Network> networks(BaseSample sample) {
    return toSampleSet(this.networks, sample);
  }

  public Set<Password> passwords(BaseSample sample) {
    return toSampleSet(this.passwords, sample);
  }

  @Override
  public void registerAssociations() {
    AssociationManager.addSupportedAssociationMultiple(this.getClass(), "networks", Network.class, 0, 2147483647, AutoLangLink.hosts_networks);
    AssociationManager.addSupportedAssociationMultiple(this.getClass(), "passwords", Password.class, 0, 2147483647, AutoLangLink.passwords_host);
  }

  protected void initLists() {
    Set<AttackStep> attackSteps = new HashSet<>();
    attackSteps.add(connect);
    attackSteps.add(authenticate);
    attackSteps.add(guessPassword);
    attackSteps.add(guessedPassword);
    attackSteps.add(access);
    this.attackSteps = Set.copyOf(attackSteps);
    Set<Defense> defenses = new HashSet<>();
    this.defenses = Set.copyOf(defenses);
    fillElementMap();
  }

  protected void initAttackSteps(DefaultValue value) {
    this.connect = new Connect();
    this.authenticate = new Authenticate();
    this.guessPassword = new GuessPassword();
    this.guessedPassword = new GuessedPassword();
    this.access = new Access();
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
    return "data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iaXNvLTg4NTktMSI/Pg0KPCEtLSBHZW5lcmF0b3I6IEFkb2JlIElsbHVzdHJhdG9yIDE5LjAuMCwgU1ZHIEV4cG9ydCBQbHVnLUluIC4gU1ZHIFZlcnNpb246IDYuMDAgQnVpbGQgMCkgIC0tPg0KPHN2ZyB2ZXJzaW9uPSIxLjEiIGlkPSJDYXBhXzEiIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyIgeG1sbnM6eGxpbms9Imh0dHA6Ly93d3cudzMub3JnLzE5OTkveGxpbmsiIHg9IjBweCIgeT0iMHB4Ig0KCSB2aWV3Qm94PSIwIDAgNTEyIDUxMiIgc3R5bGU9ImVuYWJsZS1iYWNrZ3JvdW5kOm5ldyAwIDAgNTEyIDUxMjsiIHhtbDpzcGFjZT0icHJlc2VydmUiPg0KPGc+DQoJPGc+DQoJCTxwYXRoIGQ9Ik01MTEuOTc2LDQxNi4wNjNjLTAuMDA1LTAuMDc1LTAuMDA0LTAuMTQ5LTAuMDExLTAuMjI0Yy0wLjAyNy0wLjI5Ny0wLjA3LTAuNTg4LTAuMTMxLTAuODczDQoJCQljLTAuMDA2LTAuMDI4LTAuMDE1LTAuMDU2LTAuMDIyLTAuMDg0Yy0wLjA1OS0wLjI2Mi0wLjEzMi0wLjUxOC0wLjIxOC0wLjc2OGMtMC4wMjMtMC4wNjctMC4wNDgtMC4xMzItMC4wNzMtMC4xOTgNCgkJCWMtMC4wODQtMC4yMjctMC4xNzktMC40NDgtMC4yODQtMC42NjNjLTAuMDE4LTAuMDM4LTAuMDMtMC4wNzctMC4wNDktMC4xMTVsLTQwLjExMi03OS4xMThWNzMuNzINCgkJCWMwLTguMDA2LTYuNTEzLTE0LjUxOS0xNC41MTktMTQuNTE5SDU1LjQ0MWMtOC4wMDYsMC0xNC41MTksNi41MTMtMTQuNTE5LDE0LjUxOXYyNjAuMjk4TDAuODExLDQxMy4xMzcNCgkJCWMtMC4wMTksMC4wMzgtMC4wMzEsMC4wNzctMC4wNDksMC4xMTVjLTAuMTA1LDAuMjE1LTAuMTk5LDAuNDM2LTAuMjg0LDAuNjYyYy0wLjAyNSwwLjA2Ni0wLjA1LDAuMTMyLTAuMDczLDAuMTk5DQoJCQljLTAuMDg1LDAuMjUxLTAuMTU5LDAuNTA4LTAuMjE4LDAuNzdjLTAuMDA2LDAuMDI3LTAuMDE1LDAuMDU0LTAuMDIxLDAuMDgxYy0wLjA2MSwwLjI4Ni0wLjEwNCwwLjU3Ny0wLjEzMSwwLjg3NA0KCQkJYy0wLjAwNywwLjA3NC0wLjAwNywwLjE0OS0wLjAxMSwwLjIyM0MwLjAxNCw0MTYuMjE3LDAsNDE2LjM3MSwwLDQxNi41Mjl2MTkuMDI2YzAsOS41MDksNy43MzUsMTcuMjQ0LDE3LjI0NCwxNy4yNDRoNDc3LjUxMg0KCQkJYzkuNTA5LDAsMTcuMjQ0LTcuNzM1LDE3LjI0NC0xNy4yNDR2LTE5LjAyNkM1MTIsNDE2LjM3MSw1MTEuOTg2LDQxNi4yMTcsNTExLjk3Niw0MTYuMDYzeiBNNTUuOTIzLDc0LjIwM2g0MDAuMTU0djI1NC4xMDkNCgkJCUg1NS45MjNWNzQuMjAzeiBNNTMuMDI5LDM0My4zMTFINDU4Ljk3bDMzLjMxOCw2NS43MTdoLTE2NC43OGwtOC4yNzEtMjkuOTg5Yy0xLjY4NC02LjEwNS03LjI4Mi0xMC4zNjktMTMuNjE1LTEwLjM2OWgtOTkuMjQ2DQoJCQljLTYuMzMzLDAtMTEuOTMyLDQuMjY0LTEzLjYxNSwxMC4zNjhsLTguMjcxLDI5Ljk5SDE5LjcxMUw1My4wMjksMzQzLjMxMXogTTMxMS45NDgsNDA5LjAyOUgyMDAuMDUybDYuOTkzLTI1LjM1OGg5Ny45MQ0KCQkJTDMxMS45NDgsNDA5LjAyOXogTTQ5Nyw0MzUuNTU0YzAsMS4yMzctMS4wMDcsMi4yNDQtMi4yNDQsMi4yNDRIMTcuMjQ0Yy0xLjIzNywwLTIuMjQ0LTEuMDA3LTIuMjQ0LTIuMjQ0di0xMS41MjZoNDgyVjQzNS41NTR6Ig0KCQkJLz4NCgk8L2c+DQo8L2c+DQo8Zz4NCgk8Zz4NCgkJPHBhdGggZD0iTTQzMi41NzcsMjEzLjc1NmMtNC4xNDMsMC03LjUsMy4zNTctNy41LDcuNXY3Ni4wNTVIODYuOTIzdi03Ni4wNTVjMC00LjE0My0zLjM1Ny03LjUtNy41LTcuNQ0KCQkJYy00LjE0MywwLTcuNSwzLjM1Ny03LjUsNy41djc5LjI5NGMwLDYuNDg1LDUuMjc1LDExLjc2MSwxMS43NjEsMTEuNzYxaDM0NC42MzNjNi40ODUsMCwxMS43NjEtNS4yNzUsMTEuNzYxLTExLjc2MXYtNzkuMjk0DQoJCQlDNDQwLjA3NywyMTcuMTE1LDQzNi43MiwyMTMuNzU2LDQzMi41NzcsMjEzLjc1NnoiLz4NCgk8L2c+DQo8L2c+DQo8Zz4NCgk8Zz4NCgkJPHBhdGggZD0iTTQyOC4zMTYsOTAuMjAzSDgzLjY4NGMtNi40ODUsMC0xMS43NjEsNS4yNzUtMTEuNzYxLDExLjc2MXY3OS4yOTRjMCw0LjE0MywzLjM1Nyw3LjUsNy41LDcuNWM0LjE0MywwLDcuNS0zLjM1Nyw3LjUtNy41DQoJCQl2LTc2LjA1NWgzMzguMTU0djc2LjA1NWMwLDQuMTQzLDMuMzU3LDcuNSw3LjUsNy41czcuNS0zLjM1Nyw3LjUtNy41di03OS4yOTRDNDQwLjA3Nyw5NS40NzcsNDM0LjgwMiw5MC4yMDMsNDI4LjMxNiw5MC4yMDN6Ii8+DQoJPC9nPg0KPC9nPg0KPGc+DQo8L2c+DQo8Zz4NCjwvZz4NCjxnPg0KPC9nPg0KPGc+DQo8L2c+DQo8Zz4NCjwvZz4NCjxnPg0KPC9nPg0KPGc+DQo8L2c+DQo8Zz4NCjwvZz4NCjxnPg0KPC9nPg0KPGc+DQo8L2c+DQo8Zz4NCjwvZz4NCjxnPg0KPC9nPg0KPGc+DQo8L2c+DQo8Zz4NCjwvZz4NCjxnPg0KPC9nPg0KPC9zdmc+DQo=";
  }

  public static String getIconPNG() {
    return "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAADAAAAAwCAYAAABXAvmHAAABsElEQVR4Xu2YvUrEQBRG7zbaClqLhYhoZWFhIy6iqIiv4AMI1oLFvoS9j2IhKKJYq4U2dtaijcZ7MmHZTBLdSZZk4s4Hh81mbpLzkf1jRUJCQv5dVpVTpecZOOH2a6aVDyXyFNxwLMySmMF1e8GD4IQbjoUZLNBRjiR7K+sGB1ycC6wl26/KS0Nw7UiMi3OBbrK9kJqoN1wbB1xCgSYSCoQCFTPeBfjKPlMmUhP1hmvjgItzAd8yfgXmlQdlKjVRb7g2Drg4F2j9mzgUGEFCgVCgYkZSgMe5huhKhQKLylfyvElwwMW5AFlWNhoGB+JU4FiyJ2oanP4ssCPZW+gbOBbmUrmX7JvJF3DDMTdbYhoe2AseBTcccc3kSrm1dzpkVjkcEmbLBkdcU9kV02zPXhgyHeVJsq/XIpjlmDLBkXPg3M+Ncj24wzErYk66aS/khBlmOaZscMU5zr6YE273l91zorwrk/ZCTphhlmPKBleccZc75VM5r8Cz8pazvwhmOcbe7wLOuMdNHpWLloFzpMi3mP/g2xaccY8/lmjSRuKP/RkxvzN6LQNn3ENCQtqcH61r8vaN+kBUAAAAAElFTkSuQmCC";
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
      return Host.this;
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
      return Host.this;
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
      return Host.this;
    }
  }

  public class Connect extends LocalAttackStepMin {
    public Connect() {
    }

    public Connect(Connect other) {
      super(other);
    }

    @Override
    public Set<AttackStep> getAttackStepChildren() {
      Set<AttackStep> _0 = new HashSet<>();
      _0.add(access);
      return _0;
    }

    @Override
    public void setExpectedParents(ConcreteSample sample) {
      super.setExpectedParents(sample);
      for (Network _0 : networks(sample)) {
        sample.addExpectedParent(this, _0.access);
      }
    }

    public boolean isTrace() {
      return false;
    }
  }

  public class Authenticate extends LocalAttackStepMin {
    public Authenticate() {
    }

    public Authenticate(Authenticate other) {
      super(other);
    }

    @Override
    public Set<AttackStep> getAttackStepChildren() {
      Set<AttackStep> _0 = new HashSet<>();
      _0.add(access);
      return _0;
    }

    @Override
    public void setExpectedParents(ConcreteSample sample) {
      super.setExpectedParents(sample);
      sample.addExpectedParent(this, guessedPassword);
      for (Password _0 : passwords(sample)) {
        sample.addExpectedParent(this, _0.obtain);
      }
    }

    public boolean isTrace() {
      return false;
    }
  }

  public class GuessPassword extends LocalAttackStepMin {
    public GuessPassword() {
    }

    public GuessPassword(GuessPassword other) {
      super(other);
    }

    @Override
    public Set<AttackStep> getAttackStepChildren() {
      Set<AttackStep> _0 = new HashSet<>();
      _0.add(guessedPassword);
      return _0;
    }

    public boolean isTrace() {
      return false;
    }
  }

  public class GuessedPassword extends LocalAttackStepMin {
    public GuessedPassword() {
    }

    public GuessedPassword(GuessedPassword other) {
      super(other);
    }

    @Override
    public double defaultLocalTtc(BaseSample sample, AttackStep caller) {
      return FMath.getExponentialDist(50.0).sample();
    }

    @Override
    public Set<AttackStep> getAttackStepChildren() {
      Set<AttackStep> _0 = new HashSet<>();
      _0.add(authenticate);
      return _0;
    }

    @Override
    public void setExpectedParents(ConcreteSample sample) {
      super.setExpectedParents(sample);
      sample.addExpectedParent(this, guessPassword);
    }

    public boolean isTrace() {
      return false;
    }
  }

  public class Access extends LocalAttackStepMax {
    public Access() {
    }

    public Access(Access other) {
      super(other);
    }

    @Override
    public void setExpectedParents(ConcreteSample sample) {
      super.setExpectedParents(sample);
      sample.addExpectedParent(this, connect);
      sample.addExpectedParent(this, authenticate);
    }

    public boolean isTrace() {
      return false;
    }
  }
}
