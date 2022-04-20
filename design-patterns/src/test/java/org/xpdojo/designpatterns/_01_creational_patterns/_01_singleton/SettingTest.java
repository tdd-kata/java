package org.xpdojo.designpatterns._01_creational_patterns._01_singleton;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Singleton Pattern")
class SettingTest {

    @Test
    @DisplayName("리플렉션(reflecton)을 사용하면 강제로 싱글톤 패턴을 깨트릴 수 있다")
    void sut_singleton_reflection()
            throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Setting setting1 = Setting.getInstance();
        Setting setting2 = Setting.getInstance();
        assertThat(setting1)
                .isEqualTo(setting2)
                .isSameAs(setting2);

        Constructor<Setting> constructor = Setting.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        Setting reflectedSetting = constructor.newInstance();
        assertThat(reflectedSetting)
                .isNotEqualTo(setting1)
                .isNotSameAs(setting1);
    }

    @Test
    @DisplayName("직렬화-역직렬화를 사용하면 강제로 싱글톤 패턴을 깨트릴 수 있다")
    void sut_singleton_serialize()
            throws IOException, ClassNotFoundException {
        Setting setting1 = Setting.getInstance();
        Setting setting2 = Setting.getInstance();
        assertThat(setting1)
                .isEqualTo(setting2)
                .isSameAs(setting2);

        try (ObjectOutput out = new ObjectOutputStream(new FileOutputStream("setting.tmp"))) {
            out.writeObject(setting1);
        }

        Setting setting;
        try (ObjectInput in = new ObjectInputStream(new FileInputStream("setting.tmp"))) {
            setting = (Setting) in.readObject();
        }

        assertThat(setting)
                .isNotEqualTo(setting1)
                .isNotSameAs(setting1);
    }

    @Test
    @DisplayName("enum을 사용하면 reflection을 사용해도 싱글톤 패턴을 깨트릴 수 없다")
    void sut_singleton_enum_reflection() {
        SettingEnum db1 = SettingEnum.INSTANCE;
        SettingEnum db2 = SettingEnum.INSTANCE;
        assertThat(db1)
                .isEqualTo(db2)
                .isSameAs(db2);

        assertThatThrownBy(SettingEnum.class::getDeclaredConstructor)
                .isInstanceOf(NoSuchMethodException.class);
    }

    @Test
    @DisplayName("enum을 사용하면 직렬화를 사용해도 싱글톤 패턴을 깨트릴 수 없다")
    void sut_singleton_enum_serialization() throws IOException, ClassNotFoundException {
        SettingEnum setting1 = SettingEnum.INSTANCE;
        SettingEnum setting2 = SettingEnum.INSTANCE;
        assertThat(setting1)
                .isEqualTo(setting2)
                .isSameAs(setting2);


        try (ObjectOutput out = new ObjectOutputStream(new FileOutputStream("setting.tmp"))) {
            out.writeObject(setting1);
        }

        SettingEnum setting;
        try (ObjectInput in = new ObjectInputStream(new FileInputStream("setting.tmp"))) {
            setting = (SettingEnum) in.readObject();
        }

        assertThat(setting)
                .isEqualTo(setting1)
                .isSameAs(setting1);
    }
}
