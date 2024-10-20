package sharafi.advice;

/*
 * exception during transaction
 */

public class TransactionException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int accountId;
	private long signedAmount;
	
	public TransactionException(String message, int accountId, long signedAmount) {
		super(message);
		this.accountId = accountId;
		this.signedAmount = signedAmount;
	}
	
	public int getAccountId() {
		return accountId;
	}

	public long getSignedAmount() {
		return signedAmount;
	}
}
