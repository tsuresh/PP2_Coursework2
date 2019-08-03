package util;

import models.Date;
import models.Time;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputHandeler {

    public static int promtNumberInput(Scanner scanner, int range1, int range2) {
        while (true) {
            try {
                int option = Integer.parseInt(scanner.nextLine());
                if (option >= range1 && option <= range2) {
                    return option;
                } else {
                    System.out.println("Your input has to be within range: " + range1 + "-" + range2);
                }
            } catch (NumberFormatException e) {
                System.out.println("Given input is not a number.");
            }
        }
    }

    public static int promtNumberInput(Scanner scanner) {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Given input is not a number.");
            }
        }
    }

    public static String promtStringInput(Scanner scanner) {
        String input = scanner.nextLine();
        if (input.length() == 0) {
            System.out.println("Your input cannot be blank.");
            promtStringInput(scanner);
        }
        return input;
    }


    public static Date promtDateInput(Scanner scanner) {
        String input = scanner.nextLine();
        if (input.length() == 0) {
            System.out.println("Your input cannot be blank.");
            promtStringInput(scanner);
        }
        String re1 = "((?:(?:[0-2]?\\d{1})|(?:[3][01]{1}))[-:\\/.](?:[0]?[1-9]|[1][012])[-:\\/.](?:(?:[1]{1}\\d{1}\\d{1}\\d{1})|(?:[2]{1}\\d{3})))(?![\\d])";    // DDMMYYYY 1
        Pattern p = Pattern.compile(re1, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        Matcher m = p.matcher(input);
        if (m.find()) {
            String fullDate = m.group(1);
            int day = Integer.parseInt(fullDate.split("-")[0]);
            int month = Integer.parseInt(fullDate.split("-")[1]);
            int year = Integer.parseInt(fullDate.split("-")[2]);
            return new Date(year, month, day);
        } else {
            System.out.println("Invalid date format");
            promtDateInput(scanner);
        }
        return null;
    }

    public static Time promtTimeInput(Scanner scanner) {
        String input = scanner.nextLine();
        if (input.length() == 0) {
            System.out.println("Your input cannot be blank.");
            promtStringInput(scanner);
        }
        String re1 = "((?:(?:[0-1][0-9])|(?:[2][0-3])|(?:[0-9])):(?:[0-5][0-9])(?::[0-5][0-9])?(?:\\s?(?:am|AM|pm|PM))?)";    // HourMinuteSec
        Pattern p = Pattern.compile(re1, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        Matcher m = p.matcher(input);
        if (m.find()) {
            String fullTime = m.group(1);
            int hours = Integer.parseInt(fullTime.split(":")[0]);
            int minutes = Integer.parseInt(fullTime.split(":")[1]);
            int seconds = Integer.parseInt(fullTime.split(":")[2]);
            return new Time(seconds, minutes, hours);
        } else {
            System.out.println("Invalid time format");
            promtTimeInput(scanner);
        }
        return null;
    }

}
