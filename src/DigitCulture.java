public enum DigitCulture {
    Rome("римские"), Arabic("арбабские"), NotAssigned("не присвоено");
    private String translation;
    DigitCulture(String translation)
    {
        this.translation = translation;
    }
    public String getTranslation()
    {
        return translation;
    }
}
