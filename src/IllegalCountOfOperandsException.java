class IllegalOfOperandsException extends Exception {
    private String Message;
    public String GetMessage(){return Message;}
    IllegalOfOperandsException(String message)
    {
        super(message);
        this.Message=message;
    }
}
