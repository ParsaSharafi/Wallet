package sharafi.model;

import java.sql.Time;
import java.time.LocalDate;
import org.hibernate.annotations.CreationTimestamp;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.eloyzone.jalalicalendar.DateConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import sharafi.validation.SignedAmountValidation;

@Entity
@SignedAmountValidation
public class Transaction {
	
	public Transaction(long id, Account account, long signedAmount, LocalDate date, Time time, boolean successful) {
		super();
		this.id = id;
		this.account = account;
		this.signedAmount = signedAmount;
		this.date = date;
		this.time = time;
		this.successful = successful;
	}

	public Transaction(Account account, long signedAmount, boolean successful) {
		super();
		this.account = account;
		this.signedAmount = signedAmount;
		this.successful = successful;
	}

	public Transaction() {
		
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne(optional = false)
	@JoinColumn(referencedColumnName = "person_id")
	@JsonIgnore
	private Account account;
	
	@Column(name = "signed_amount")
	private long signedAmount;
	
    @Temporal(TemporalType.DATE)
	@CreationTimestamp
    private LocalDate date;
	
    @Temporal(TemporalType.TIME)
	@CreationTimestamp
	private Time time;
	
	private boolean successful;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public long getSignedAmount() {
		return signedAmount;
	}

	public void setSignedAmount(long signedAmount) {
		this.signedAmount = signedAmount;
	}
	
	public String getDate() {
		return new DateConverter().gregorianToJalali(date.getYear(), date.getMonth(), date.getDayOfMonth()).toString();
	}

	public void setDate(String date) {
		String[] dateValues = date.split("[-]");
		this.date = new DateConverter().jalaliToGregorian(Integer.valueOf(dateValues[0]), Integer.valueOf(dateValues[1]), Integer.valueOf(dateValues[2]));
	}
	
	public String getTime() {
		return time.toString();
	}

	public void setTime(Time time) {
		this.time = time;
	}

	public boolean isSuccessful() {
		return successful;
	}

	public void setSuccessful(boolean successful) {
		this.successful = successful;
	}

	@Override
	public String toString() {
		return "Transaction [id=" + id + ", account=" + account + ", signedAmount=" + signedAmount + ", date=" + date
				+ ", time=" + time + ", successful=" + successful + "]";
	}
}
