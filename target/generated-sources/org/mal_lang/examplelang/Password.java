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
import com.foreseeti.corelib.util.FProb;
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
    name = "Password"
)
public class Password extends MultiParentAsset {
  protected Set<AttackStep> attackSteps;

  protected Set<Defense> defenses;

  @Association(
      index = 1,
      name = "host"
  )
  public FProb<Host> host;

  @Association(
      index = 2,
      name = "user"
  )
  public FProb<User> user;

  @Association(
      index = 3,
      name = "obtain"
  )
  @Display
  public Obtain obtain;

  public Password() {
    this(DefaultValue.False);
  }

  public Password(DefaultValue value) {
    initAttackSteps(value);
    initLists();
  }

  public Password(Password other) {
    super(other);
    obtain = new Obtain(other.obtain);
    initLists();
  }

  public Host host(BaseSample sample) {
    return toSample(this.host, sample);
  }

  public User user(BaseSample sample) {
    return toSample(this.user, sample);
  }

  @Override
  public void registerAssociations() {
    AssociationManager.addSupportedAssociationMultiple(this.getClass(), "host", Host.class, 1, 1, AutoLangLink.passwords_host);
    AssociationManager.addSupportedAssociationMultiple(this.getClass(), "user", User.class, 1, 1, AutoLangLink.passwords_user);
  }

  protected void initLists() {
    Set<AttackStep> attackSteps = new HashSet<>();
    attackSteps.add(obtain);
    this.attackSteps = Set.copyOf(attackSteps);
    Set<Defense> defenses = new HashSet<>();
    this.defenses = Set.copyOf(defenses);
    fillElementMap();
  }

  protected void initAttackSteps(DefaultValue value) {
    this.obtain = new Obtain();
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
    return "data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iaXNvLTg4NTktMSI/Pg0KPCEtLSBHZW5lcmF0b3I6IEFkb2JlIElsbHVzdHJhdG9yIDE5LjAuMCwgU1ZHIEV4cG9ydCBQbHVnLUluIC4gU1ZHIFZlcnNpb246IDYuMDAgQnVpbGQgMCkgIC0tPg0KPHN2ZyB2ZXJzaW9uPSIxLjEiIGlkPSJDYXBhXzEiIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyIgeG1sbnM6eGxpbms9Imh0dHA6Ly93d3cudzMub3JnLzE5OTkveGxpbmsiIHg9IjBweCIgeT0iMHB4Ig0KCSB2aWV3Qm94PSIwIDAgNTEyIDUxMiIgc3R5bGU9ImVuYWJsZS1iYWNrZ3JvdW5kOm5ldyAwIDAgNTEyIDUxMjsiIHhtbDpzcGFjZT0icHJlc2VydmUiPg0KPGc+DQoJPGc+DQoJCTxnPg0KCQkJPGNpcmNsZSBjeD0iMzcwIiBjeT0iMzQ2IiByPSIyMCIvPg0KCQkJPHBhdGggZD0iTTQ2MCwzNjJjMTEuMDQ2LDAsMjAtOC45NTQsMjAtMjB2LTc0YzAtNDQuMTEyLTM1Ljg4OC04MC04MC04MGgtMjQuMDM3di03MC41MzRDMzc1Ljk2Myw1Mi42OTUsMzIyLjEzMSwwLDI1NS45NjMsMA0KCQkJCXMtMTIwLDUyLjY5NS0xMjAsMTE3LjQ2NlYxODhIMTEyYy00NC4xMTIsMC04MCwzNS44ODgtODAsODB2MTY0YzAsNDQuMTEyLDM1Ljg4OCw4MCw4MCw4MGgyODhjNDQuMTEyLDAsODAtMzUuODg4LDgwLTgwDQoJCQkJYzAtMTEuMDQ2LTguOTU0LTIwLTIwLTIwYy0xMS4wNDYsMC0yMCw4Ljk1NC0yMCwyMGMwLDIyLjA1Ni0xNy45NDQsNDAtNDAsNDBIMTEyYy0yMi4wNTYsMC00MC0xNy45NDQtNDAtNDBWMjY4DQoJCQkJYzAtMjIuMDU2LDE3Ljk0NC00MCw0MC00MGgyODhjMjIuMDU2LDAsNDAsMTcuOTQ0LDQwLDQwdjc0QzQ0MCwzNTMuMDQ2LDQ0OC45NTQsMzYyLDQ2MCwzNjJ6IE0zMzUuOTYzLDE4OGgtMTYwdi03MC41MzQNCgkJCQljMC00Mi43MTUsMzUuODg4LTc3LjQ2Niw4MC03Ny40NjZzODAsMzQuNzUxLDgwLDc3LjQ2NlYxODh6Ii8+DQoJCQk8Y2lyY2xlIGN4PSIyMTkiIGN5PSIzNDYiIHI9IjIwIi8+DQoJCQk8Y2lyY2xlIGN4PSIxNDQiIGN5PSIzNDYiIHI9IjIwIi8+DQoJCQk8Y2lyY2xlIGN4PSIyOTQiIGN5PSIzNDYiIHI9IjIwIi8+DQoJCTwvZz4NCgk8L2c+DQo8L2c+DQo8Zz4NCjwvZz4NCjxnPg0KPC9nPg0KPGc+DQo8L2c+DQo8Zz4NCjwvZz4NCjxnPg0KPC9nPg0KPGc+DQo8L2c+DQo8Zz4NCjwvZz4NCjxnPg0KPC9nPg0KPGc+DQo8L2c+DQo8Zz4NCjwvZz4NCjxnPg0KPC9nPg0KPGc+DQo8L2c+DQo8Zz4NCjwvZz4NCjxnPg0KPC9nPg0KPGc+DQo8L2c+DQo8L3N2Zz4NCg==";
  }

  public static String getIconPNG() {
    return "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAADAAAAAwCAYAAABXAvmHAAADJklEQVR4Xu2Zy68MQRTGP4SEIIJ4hZh4JDaId0TEiB1LCxKRu8CCv0AsZGLDQiwsRcKGndfKclYssLES8Uy8SYj3ArnOl+q+fU7NVM/MzXRXL/qXfOk7+U5VnTM9Vbe6Ghgu80RHRDdFT0XfEz0R3RAdTmIqxyzRGdEv0WgPMeZ00qYSrBE9R2eivfRMtBqR2SL6gc7kqI+ie6L7yd++T/HntRmRWCJ6B5vQX9El0XoVl7JBdBkuRrd5K1qchZUHJ6X/jTd1QICdok+wba+ZiBLYBpvAT9FGE5HPJrg2uo+tJqJgLsIOftzafXECto8L1i6OibCTkhNxqonoj2mwC8AH0QQTURALYb85zoXxcgu2r/nWLgauJnrQs9YeiHOwfa2zdjE0YQc9adzBaMH2tcO4BdFEXcAYLdQFDE4TdQFjtFBCAXNFu0Qjibjv14NeV96g4oNP2g/F54TU45gce9xwR3kbnbvHMsWxmUO33W0ux0R/0NlhLDEX5tQXB9DZQVXE3HLhb+4rbKPPovNwD+sjJYljcUyOParE3HLnBVcU3eChaJGJKBeOzRx0TrmrHp9fdfBaa0eBOeicmGOQL8gCX3heTJhLmhdzDKIrbXteTJiLzi1IXUBB1AXEZqgFLIP7Z8Orz1I4b6VvwJ2+0VvlG8Js0UGEz5aGVgCPE7/B+bzyc8oCZMswD610gXOQncb9hi1isuhx4v2DO7nzGVoBe2Fj+Dllj+fpfQu3xto7pLzlnndKeSlXkfk8Sw2iO2p7HuE+5DWcz6vel/C8/2XivYe7Iykz4F5y0OOd0Hdukuhu4vHO8djGZ4XojuiRaLfnGXoVQJhoM7n6zITz+Jv2mQ7ndduMTRFtxxBOqfspoNLUBcSmygXwVRRXOk76IFUtgE9oaV5XPM+gTyC4bFUFfplpXlxqg7xBFsjnz1JeOPSAOejndOYYxH/hcNTaUWAOOifmGGQ/bDBvV8wiOLb/MpA5BuH7rwewDSjeQs6JdkniWP7xDsXcmGMuDbgXbn7j2GJODfRJA93vRCwxlwYGhLN/H9ykeYXOTosWx+TYzCG4Gv4Hptxzke6enyQAAAAASUVORK5CYII=";
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
      return Password.this;
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
      return Password.this;
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
      return Password.this;
    }
  }

  public class Obtain extends LocalAttackStepMin {
    public Obtain() {
    }

    public Obtain(Obtain other) {
      super(other);
    }

    @Override
    public Set<AttackStep> getAttackStepChildren() {
      Set<AttackStep> _0 = new HashSet<>();
      if (host != null) {
        _0.add(host(null).authenticate);
      }
      return _0;
    }

    @Override
    public void setExpectedParents(ConcreteSample sample) {
      super.setExpectedParents(sample);
      if (user != null) {
        sample.addExpectedParent(this, user(sample).phish);
      }
    }

    public boolean isTrace() {
      return false;
    }
  }
}
