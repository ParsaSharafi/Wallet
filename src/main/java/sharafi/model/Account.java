package sharafi.model;

import java.time.LocalDate;
import java.util.List;
import org.hibernate.annotations.CreationTimestamp;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.eloyzone.jalalicalendar.DateConverter;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import sharafi.validation.UniqueAccountValidation;

@Entity
@UniqueAccountValidation
public class Account {
	
	public Account(int id,
			@Pattern(regexp = "^[0-9]+$", message = "شماره‌حساب فقط می‌تواند رقم باشد") @NotBlank(message = "شماره‌حساب نباید خالی باشد") String accountNumber,
			@Min(value = 10000, message = "کف موجودی حساب باید ده‌هزار ریال باشد") long balance, LocalDate openingDate,
			@Pattern(regexp = "^(IR)[0-9]{24}$", message = "شبای ایرانی معتبر نیست") @NotBlank(message = "شبا نباید خالی باشد") String iban,
			Person person, List<Transaction> transactions) {
		super();
		this.id = id;
		this.accountNumber = accountNumber;
		this.balance = balance;
		this.openingDate = openingDate;
		this.iban = iban;
		this.person = person;
		this.transactions = transactions;
	}

	public Account() {
		
	}

	@Id
	@JsonProperty("ownerId")
	private int id;
	
	@Column(unique = true, length = 20, name = "account_number")
	@Pattern(regexp="^[0-9]+$", message = "شماره‌حساب فقط می‌تواند رقم باشد")
	@NotBlank(message = "شماره‌حساب نباید خالی باشد")
	private String accountNumber;
	
	@Min(value = 10000, message = "کف موجودی حساب باید ده‌هزار ریال باشد")
	private long balance;
	
	@Column(name = "opening_date")
    @Temporal(TemporalType.DATE)
	@CreationTimestamp
    private LocalDate openingDate;
	
	@Column(unique = true, length = 26)
	@Pattern(regexp="^(IR)[0-9]{24}$", message = "شبای ایرانی معتبر نیست")
	@NotBlank(message = "شبا نباید خالی باشد")
	private String iban;
	
	@OneToOne(optional = false, fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn
	@JsonIgnore
	private Person person;
	
	@OneToMany(mappedBy = "account", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Transaction> transactions;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getOpeningDate() {
		if (openingDate == null)
			return null;
		return new DateConverter().gregorianToJalali(openingDate.getYear(), openingDate.getMonth(), openingDate.getDayOfMonth()).toString();
	}

	public void setOpeningDate(String openingDate) {
		try {
			String[] dateValues = openingDate.split("[-]");
			this.openingDate = new DateConverter().jalaliToGregorian(Integer.valueOf(dateValues[0]), Integer.valueOf(dateValues[1]), Integer.valueOf(dateValues[2]));
		} catch (Exception e) {
			this.openingDate = null;
		}
	}
	
	public long getBalance() {
		return balance;
	}

	public void setBalance(long balance) {
		this.balance = balance;
	}

	public String getIban() {
		return iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

	@Override
	public String toString() {
		return "Account [id=" + id + ", accountNumber=" + accountNumber + ", balance=" + balance + ", openingDate="
				+ openingDate + ", iban=" + iban + ", person=" + person.getFirstName() + " " + person.getLastName() + "]";
	}
}