package com.rupp.tola.dev.scoring_management_system.utils;

import java.time.Year;

public class StudentCodeGenerateUtils {
      private static int counter = 0;
      public static String generator() {
            String year = String.valueOf(Year.now().getValue());
            return year + String.format("%04d", counter++);
      }
}
