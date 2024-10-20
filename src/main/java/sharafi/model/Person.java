package sharafi.model;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.eloyzone.jalalicalendar.DateConverter;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import sharafi.validation.MilitaryServiceValidation;
import sharafi.validation.UniquePersonValidation;

@Entity
@MilitaryServiceValidation
@UniquePersonValidation
public class Person {
	
	public Person(int id,
			@Pattern(regexp = "^[0-9]{10}$", message = "کد ملی باید ده‌رقم باشد") @NotBlank(message = "کد ملی نباید خالی باشد") String nationalId,
			@Pattern(regexp = "^09[0-9]{9}$", message = "شماره موبایل معتبر نیست") @NotBlank(message = "شماره موبایل نباید خالی باشد") String phoneNumber,
			@NotBlank(message = "نام نباید خالی باشد") String firstName,
			@NotBlank(message = "نام خانوادگی نباید خالی باشد") String lastName,
			@Past(message = "تاریخ تولد باید در گذشته باشد") @NotNull(message = "تاریخ تولد نباید خالی باشد") LocalDate birthDate,
			@NotNull(message = "جنسیت باید انتخاب شود") Gender gender, MilitaryServiceStatus militaryServiceStatus,
			@Email(message = "ایمیل معتبر نیست") @NotBlank(message = "ایمیل نباید خالی باشد") String email,
			Account account, CustomUser user) {
		super();
		this.id = id;
		this.nationalId = nationalId;
		this.phoneNumber = phoneNumber;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthDate = birthDate;
		this.gender = gender;
		this.militaryServiceStatus = militaryServiceStatus;
		this.email = email;
		this.account = account;
		this.user = user;
	}

	public Person() {
		
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(unique = true, length = 10, name = "national_id")
	@Pattern(regexp="^[0-9]{10}$", message = "کد ملی باید ده‌رقم باشد")
	@NotBlank(message = "کد ملی نباید خالی باشد")
	private String nationalId;
	
	@Column(unique = true, length = 11, name = "phone_number")
	@Pattern(regexp="^09[0-9]{9}$", message = "شماره موبایل معتبر نیست")
	@NotBlank(message = "شماره موبایل نباید خالی باشد")
	private String phoneNumber;
	
	@Column(length = 50, name = "first_name")
	@NotBlank(message = "نام نباید خالی باشد")
	private String firstName;
	
	@Column(length = 50, name = "last_name")
	@NotBlank(message = "نام خانوادگی نباید خالی باشد")
	private String lastName;
	
	@Column(name = "birth_date")
	@Past(message = "تاریخ تولد نمی‌تواند در آینده باشد")
	@NotNull(message = "تاریخ تولد باید در گذشته باشد")
	private LocalDate birthDate;
	
	@Enumerated(EnumType.STRING)
	@NotNull(message = "جنسیت باید انتخاب شود")
	private Gender gender;
	
	@Column(name = "military_service_status")
	@Enumerated(EnumType.STRING)
	private MilitaryServiceStatus militaryServiceStatus;
	
	@Column(unique = true)
	@Email(message = "ایمیل معتبر نیست")
	@NotBlank(message = "ایمیل نباید خالی باشد")
	private String email;
	
	@OneToOne(mappedBy = "person", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    @PrimaryKeyJoinColumn
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private Account account;
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(referencedColumnName = "username", name = "username", updatable = false)
	@JsonIgnore
    private CustomUser user;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNationalId() {
		return nationalId;
	}

	public void setNationalId(String nationalId) {
		this.nationalId = nationalId;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getBirthDate() {
		if (birthDate == null)
			return null;
		return new DateConverter().gregorianToJalali(birthDate.getYear(), birthDate.getMonth(), birthDate.getDayOfMonth()).toString();
	}

	public void setBirthDate(String birthDate) {
		try {
			String[] dateValues = birthDate.split("[-]");
			this.birthDate = new DateConverter().jalaliToGregorian(Integer.valueOf(dateValues[0]), Integer.valueOf(dateValues[1]), Integer.valueOf(dateValues[2]));
		} catch (Exception e) {
			this.birthDate = null;
		}
	}
	
	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public MilitaryServiceStatus getMilitaryServiceStatus() {
		return militaryServiceStatus;
	}

	public void setMilitaryServiceStatus(MilitaryServiceStatus militaryServiceStatus) {
		this.militaryServiceStatus = militaryServiceStatus;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public CustomUser getUser() {
		return user;
	}

	public void setUser(CustomUser user) {
		this.user = user;
	}
}
