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
import com.foreseeti.corelib.util.FProbSet;
import com.foreseeti.simulator.AttackStep;
import com.foreseeti.simulator.AttackStepMax;
import com.foreseeti.simulator.AttackStepMin;
import com.foreseeti.simulator.Defense;
import com.foreseeti.simulator.MultiParentAsset;
import java.util.HashSet;
import java.util.Set;

@DisplayClass(
    category = Category.System
)
@TypeName(
    name = "Network"
)
public class Network extends MultiParentAsset {
  protected Set<AttackStep> attackSteps;

  protected Set<Defense> defenses;

  @Association(
      index = 1,
      name = "hosts"
  )
  public FProbSet<Host> hosts = new FProbSet<>();

  @Association(
      index = 2,
      name = "access"
  )
  @Display
  public Access access;

  public Network() {
    this(DefaultValue.False);
  }

  public Network(DefaultValue value) {
    initAttackSteps(value);
    initLists();
  }

  public Network(Network other) {
    super(other);
    access = new Access(other.access);
    initLists();
  }

  public Set<Host> hosts(BaseSample sample) {
    return toSampleSet(this.hosts, sample);
  }

  @Override
  public void registerAssociations() {
    AssociationManager.addSupportedAssociationMultiple(this.getClass(), "hosts", Host.class, 0, 2147483647, AutoLangLink.hosts_networks);
  }

  protected void initLists() {
    Set<AttackStep> attackSteps = new HashSet<>();
    attackSteps.add(access);
    this.attackSteps = Set.copyOf(attackSteps);
    Set<Defense> defenses = new HashSet<>();
    this.defenses = Set.copyOf(defenses);
    fillElementMap();
  }

  protected void initAttackSteps(DefaultValue value) {
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
    return "data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iaXNvLTg4NTktMSI/Pg0KPCEtLSBHZW5lcmF0b3I6IEFkb2JlIElsbHVzdHJhdG9yIDE5LjAuMCwgU1ZHIEV4cG9ydCBQbHVnLUluIC4gU1ZHIFZlcnNpb246IDYuMDAgQnVpbGQgMCkgIC0tPg0KPHN2ZyB2ZXJzaW9uPSIxLjEiIGlkPSJDYXBhXzEiIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyIgeG1sbnM6eGxpbms9Imh0dHA6Ly93d3cudzMub3JnLzE5OTkveGxpbmsiIHg9IjBweCIgeT0iMHB4Ig0KCSB2aWV3Qm94PSIwIDAgNTUgNTUiIHN0eWxlPSJlbmFibGUtYmFja2dyb3VuZDpuZXcgMCAwIDU1IDU1OyIgeG1sOnNwYWNlPSJwcmVzZXJ2ZSI+DQo8cGF0aCBkPSJNNDksMGMtMy4zMDksMC02LDIuNjkxLTYsNmMwLDEuMDM1LDAuMjYzLDIuMDA5LDAuNzI2LDIuODZsLTkuODI5LDkuODI5QzMyLjU0MiwxNy42MzQsMzAuODQ2LDE3LDI5LDE3DQoJcy0zLjU0MiwwLjYzNC00Ljg5OCwxLjY4OGwtNy42NjktNy42NjlDMTYuNzg1LDEwLjQyNCwxNyw5Ljc0LDE3LDljMC0yLjIwNi0xLjc5NC00LTQtNFM5LDYuNzk0LDksOXMxLjc5NCw0LDQsNA0KCWMwLjc0LDAsMS40MjQtMC4yMTUsMi4wMTktMC41NjdsNy42NjksNy42NjlDMjEuNjM0LDIxLjQ1OCwyMSwyMy4xNTQsMjEsMjVzMC42MzQsMy41NDIsMS42ODgsNC44OTdMMTAuMDI0LDQyLjU2Mg0KCUM4Ljk1OCw0MS41OTUsNy41NDksNDEsNiw0MWMtMy4zMDksMC02LDIuNjkxLTYsNnMyLjY5MSw2LDYsNnM2LTIuNjkxLDYtNmMwLTEuMDM1LTAuMjYzLTIuMDA5LTAuNzI2LTIuODZsMTIuODI5LTEyLjgyOQ0KCWMxLjEwNiwwLjg2LDIuNDQsMS40MzYsMy44OTgsMS42MTl2MTAuMTZjLTIuODMzLDAuNDc4LTUsMi45NDItNSw1LjkxYzAsMy4zMDksMi42OTEsNiw2LDZzNi0yLjY5MSw2LTZjMC0yLjk2Ny0yLjE2Ny01LjQzMS01LTUuOTENCgl2LTEwLjE2YzEuNDU4LTAuMTgzLDIuNzkyLTAuNzU5LDMuODk4LTEuNjE5bDcuNjY5LDcuNjY5QzQxLjIxNSwzOS41NzYsNDEsNDAuMjYsNDEsNDFjMCwyLjIwNiwxLjc5NCw0LDQsNHM0LTEuNzk0LDQtNA0KCXMtMS43OTQtNC00LTRjLTAuNzQsMC0xLjQyNCwwLjIxNS0yLjAxOSwwLjU2N2wtNy42NjktNy42NjlDMzYuMzY2LDI4LjU0MiwzNywyNi44NDYsMzcsMjVzLTAuNjM0LTMuNTQyLTEuNjg4LTQuODk3bDkuNjY1LTkuNjY1DQoJQzQ2LjA0MiwxMS40MDUsNDcuNDUxLDEyLDQ5LDEyYzMuMzA5LDAsNi0yLjY5MSw2LTZTNTIuMzA5LDAsNDksMHogTTExLDljMC0xLjEwMywwLjg5Ny0yLDItMnMyLDAuODk3LDIsMnMtMC44OTcsMi0yLDINCglTMTEsMTAuMTAzLDExLDl6IE02LDUxYy0yLjIwNiwwLTQtMS43OTQtNC00czEuNzk0LTQsNC00czQsMS43OTQsNCw0UzguMjA2LDUxLDYsNTF6IE0zMyw0OWMwLDIuMjA2LTEuNzk0LDQtNCw0cy00LTEuNzk0LTQtNA0KCXMxLjc5NC00LDQtNFMzMyw0Ni43OTQsMzMsNDl6IE0yOSwzMWMtMy4zMDksMC02LTIuNjkxLTYtNnMyLjY5MS02LDYtNnM2LDIuNjkxLDYsNlMzMi4zMDksMzEsMjksMzF6IE00Nyw0MWMwLDEuMTAzLTAuODk3LDItMiwyDQoJcy0yLTAuODk3LTItMnMwLjg5Ny0yLDItMlM0NywzOS44OTcsNDcsNDF6IE00OSwxMGMtMi4yMDYsMC00LTEuNzk0LTQtNHMxLjc5NC00LDQtNHM0LDEuNzk0LDQsNFM1MS4yMDYsMTAsNDksMTB6Ii8+DQo8Zz4NCjwvZz4NCjxnPg0KPC9nPg0KPGc+DQo8L2c+DQo8Zz4NCjwvZz4NCjxnPg0KPC9nPg0KPGc+DQo8L2c+DQo8Zz4NCjwvZz4NCjxnPg0KPC9nPg0KPGc+DQo8L2c+DQo8Zz4NCjwvZz4NCjxnPg0KPC9nPg0KPGc+DQo8L2c+DQo8Zz4NCjwvZz4NCjxnPg0KPC9nPg0KPGc+DQo8L2c+DQo8L3N2Zz4NCg==";
  }

  public static String getIconPNG() {
    return "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAADAAAAAwCAYAAABXAvmHAAAEoElEQVR4Xs2ZachmYxjH/4xlhqxjytgGMVmSyIwsRQhZPgwZS9JgJFlj4outREShkJ0PlqxFKMY0jS+2mMwoSjOlaDDZ17Ffv65zd+738p7znOd5j/ecX/17eu7rPudc133u5brvI/WbjU2LTMtNv5vWmp41zckr9ZUtTW+b/jGtM71n+rz4/4dpYVm1nzwtd/YJeTCJY0xfmP5Uj9/EbNPfpndMU4INCILg6E6N2E7eF+8xnW3aYKy5degeOHhhNGR8Jh8TAznA9I38hkn0zWl5pZa5RP6c+dGQ8b7p11g4HswADJrzTHuYHpbf/PqsTtscK3/GHdFQwJhgYH8QDZGt5X3x+axsI3nkb2RlbcMzfpQ7eWCwMSYY2ARIt65lqnz+fTMrmymfAV7PytpkPdN9KrsrQdxpOtl0scqplQHOOjGQNJ09Z7rC9GHx/7q8Ukvkzr9rOkPl3J8Ln7YorhnINvLWThfT+rTKz6Yjs3oTJTq/VVFOKx8nb7xzTbsX5UOzj+kE006mo02/qL0gqpz/X2kriE6cT0w0iE6dTwwKYgfTzvrvoOuF84kYBAP/Vo2dQVhPmI7PNK2vHjmfyIMgR8G5L01Pmh4xLZavKZR/Wvz2xvnEBXLHfpPnMzHpozu9KK/zk2n6WHP30KJ/mU6Mhgz6/jPyIOqyzUnncLlTj4Xy8WCMfG9aFQ1dcqM8gKOioYLH5fV3i4auoOVxaPtoqOAaef0joqErHtRwLXqTvP4h0dAVl8kdWhDKq1gmTwx7M43Oku/cVpg2DLbIwfKF7dVo6Jq75W+BhWu80wTYVb6Q0fpxt9UpzO8PyANApA3Hq9w1sZNjC5gOCNiWjpc7dQLO3y93jK0eeQ4tnILJtcZ0g+oTwEklOp9O0faWT5WfFDZSiHNMmxf2mAB2Qp4S587n3C637xsN6jiIJs5DXQDQSRBNnYdBAcCkBjGM89AkAMiD4BTwLvlegrOgQetKY4Z1HpoGACmIOHORpjc6xKpjFOdhmABoaaZZUm2C2db0kPz6K7N6texi2k/ldAejOg/DBMDUS917s7LN5GkH03AtbAc5c0+vjf0rHxBmaXTnYZgA2GLi7JKsbH/59ZyKV0LEVPpOfgbKAOLomrJ1xe8ozrPzSntgFrGq/CjnBXn9V0w3y7sU29TD8ko5nPpyAR8Mdgy2ywsbR93DOM9mnsQunUIk4Qz5UR0z5M6na3g2Z6KVLJVHuGc0FPBtgBvNjYYaXpJfQ2p9qbyRbpFPkeRI88uqldCYPHOTaIgw4j+OhRkL5M6cH8qrOFVlF+BDRc5e8hblTTA4W+EH00exMOMsuUMXRUMF9HkGYuyOiWvl95sXDaPCpyK6UNW5+1PyBzbdu66Wb1iqIFXgfmSqrXCa/IZ8umHhyEknbcvla0ET6I50k6oZ53T5Pa+KhonwqPymX8uPR24zvVWUsYNigWlK2hvweShCI7wmtx8UbBOCGzNlfiW/OaIfv6zmxySJ2fLt4remk7LyTVUuiPlC1Sq8dj4lHSr/xDoqp8gPeHGWRmERJCj+85EwdtVeQrfj+JAjdiaJlaar5W+iNf4FhYtpTCrnwfkAAAAASUVORK5CYII=";
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
      return Network.this;
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
      return Network.this;
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
      return Network.this;
    }
  }

  public class Access extends LocalAttackStepMin {
    public Access() {
    }

    public Access(Access other) {
      super(other);
    }

    @Override
    public Set<AttackStep> getAttackStepChildren() {
      Set<AttackStep> _0 = new HashSet<>();
      for (Host _1 : hosts(null)) {
        _0.add(_1.connect);
      }
      return _0;
    }

    public boolean isTrace() {
      return false;
    }
  }
}
