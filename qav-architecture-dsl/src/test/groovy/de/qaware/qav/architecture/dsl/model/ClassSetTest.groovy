package de.qaware.qav.architecture.dsl.model

import com.google.common.collect.Lists
import de.qaware.qav.test.beans.BeanTestUtil
import org.junit.Test

/**
 * Tests for {@link ClassSet}.
 *
 * @author QAware GmbH
 */
class ClassSetTest {

    @Test
    void testOnePackage() {
        ClassSet classSet = new ClassSet("t1", Lists.newArrayList("playground.*"))

        assert classSet.matches("playground.my.Clazz")
        assert classSet.matches("playground.Clazz")
    }

    @Test
    void testMatches() {
        ClassSet classSet = new ClassSet("t1", Lists.newArrayList("my.impl.*"))

        assert classSet.matches("my.impl.AbcImpl")
        assert !classSet.matches("my.api.Abc")
    }

    @Test
    void testMatchesMorePatterns() {
        ClassSet classSet = new ClassSet("t1", Lists.newArrayList("my.impl.a.*", "my.impl.b.*"))

        assert classSet.matches("my.impl.a.AbcImpl")
        assert classSet.matches("my.impl.b.AbcImpl")
        assert !classSet.matches("my.api.Abc")
    }

    @Test
    void testMatchesBadInput() {
        ClassSet classSet = new ClassSet("t1", Lists.newArrayList("my.impl.a.*", "my.impl.b.*"))

        assert !classSet.matches("")
        assert !classSet.matches(null)
    }

    @Test
    void testOtherSeparators() {
        ClassSet classSet = new ClassSet("t1", Lists.newArrayList("my#impl#a#*", "my#impl#b#*"))
        classSet.setPathSeparator("#")

        assert classSet.matches("my#impl#a#AbcImpl")
        assert classSet.matches("my#impl#b#AbcImpl")
        assert classSet.matches("my#impl#b")
        assert !classSet.matches("my#api#Abc")

        assert !classSet.matches("my.impl.a.AbcImpl")
    }

    @Test
    void testEquals() {
        ClassSet cs1 = new ClassSet("t1", Lists.newArrayList("my.api.*"))
        ClassSet cs2 = new ClassSet("t1", Lists.newArrayList("my.api.*"))
        ClassSet cs3 = new ClassSet("t2", Lists.newArrayList("my.other.api.*"))

        BeanTestUtil.checkEqualsMethod(cs1, cs2, cs3)
    }
}
