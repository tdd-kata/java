package org.xpdojo.designpatterns._01_creational_patterns._01_singleton;

import java.io.Serializable;

public class Setting implements Serializable {
    private static Setting INSTANCE;

    private Setting() {
    }

    /**
     * static inner class to implement lazy instantiation
     */
    private static class SettingHolder {
        // eager initialization
        private static final Setting INSTANCE = new Setting();
    }

    /**
     * synchronized 키워드 사용
     *
     * @return 싱글톤 인스턴스
     * @see <a href="https://rules.sonarsource.com/java/tag/multi-threading/RSPEC-2168">Double-checked locking should not be used</a>
     */
    public static Setting getInstance() {
        return SettingHolder.INSTANCE;
    }

    /**
     * synchronized 키워드 사용
     *
     * @return 싱글톤 인스턴스
     * @see <a href="https://rules.sonarsource.com/java/tag/multi-threading/RSPEC-2168">Double-checked locking should not be used</a>
     */
    public static synchronized Setting getInstanceSynchronized() {
        if (INSTANCE == null) {
            INSTANCE = new Setting();
        }
        return INSTANCE;
    }

    /**
     * double-checked locking
     *
     * @return 싱글톤 인스턴스
     * @see <a href="https://rules.sonarsource.com/java/tag/multi-threading/RSPEC-2168">Double-checked locking should not be used</a>
     */
    public static Setting getInstance_Double_Checked_Locking() {
        if (INSTANCE == null) {
            synchronized (Setting.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Setting();
                }
            }
        }
        return INSTANCE;
    }

    public String query(String sql) {
        return sql;
    }

    public static void main(String[] args) {
        Setting db1 = Setting.getInstance();
        db1.query("SELECT ...");

        Setting db2 = Setting.getInstance();
        db2.query("SELECT ...");
    }
}
