package pl.bartoszmech.domain.offer;

class OfferValidator {
    public static final int MINIMUM_TITLE_LENGTH = 1;
    public static final int MAXIMUM_TITLE_LENGTH = 50;
    public static final int MINIMUM_COMPANY_LENGTH = 1;
    public static final int MAXIMUM_COMPANY_LENGTH = 100;

    boolean validate(String title, String company, String salary) {
        return isNotBetween(title, MINIMUM_TITLE_LENGTH, MAXIMUM_TITLE_LENGTH)
                || isNotBetween(company, MINIMUM_COMPANY_LENGTH, MAXIMUM_COMPANY_LENGTH)
                || salary.isBlank();

    }

    private boolean isNotBetween(String str, int start, int end) {
        String trimmedStr = str.trim();
        return !(trimmedStr.length() >= start && trimmedStr.length() <= end);
    }
}
