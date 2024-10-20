INSERT INTO costum_user(username, password) VALUES ('1234567890', '$2a$12$X4DrDAUexhAFzKdsCmkf.OPrxAlHx.u891dqimMuq4t/9tNGpxfWS'), 
('1237733863', '$2a$12$rooXP4fHC2/8cZyVaoWRBuDyr.Koth/86tpmhI4zg4HJQcyHbBElO'), 
('1234901763', '$2a$12$OMA8h3eDE.Ms5zVjQkJq1..JtxeW2NV4ph/zKTT8bgGlgw1/c4lm.'), 
('0034900852', '$2a$12$HAuW27UrMeivmN//EwLrOOeh.kqKokzghs4j/9DOnUp7HYAEYgv9C'), 
('0004934567', '$2a$12$gC/zPDHiU820K4TDQkJ6eOJeAL4LlDPouysjbnVYpJV89pCm6EHvy');

INSERT INTO person(national_id, phone_number, first_name, last_name, birth_date, gender, military_service_status, email, username) 
VALUES ('1234567890', '09123456789', 'پارسا', 'شرفی', '2000-05-20', 'مرد', 'معاف', 'parsa@gmail.com', '1234567890'), 
('1237733863', '09213452444', 'آزاده', 'برزویی', '1999-11-01', 'زن', null, 'azade@gmail.com', '1237733863'), 
('1234901763', '09358402563', 'جعفر', 'خلعتبری', '1978-12-18', 'مرد', 'پایان‌یافته', 'jafar@gmail.com', '1234901763'), 
('0034900852', '09937168420', 'حسین', 'طبری', '2012-09-27', 'مرد', null, 'hosein@email.com', '0034900852'), 
('0004934567', '09255452599', 'رعنا', 'شیرمحمدی', '1966-03-08', 'زن', null, 'rana@yahoo.co.uk', '0004934567');

INSERT INTO account (account_number, balance, opening_date, iban, person_id) 
VALUES ('389121', 43245324, '2000-05-20', 'IR123456789098765432100001', 1), 
('654332', 43324, '2000-05-20', 'IR123456780000005432100005', 2), 
('38191123', 12345677, '2024-01-01', 'IR999888789098765432100001', 3), 
('3155758', 20000000, '2007-06-30', 'IR123456789000005432107866', 4);

INSERT INTO transaction(account_person_id, signed_amount, date, time, successful) VALUES (1, 654354, '2024-01-01', '09:56', true), 
(1, -50000, '2024-02-21', '08:12', true), (4, 2310000, '2024-03-03', '11:33', true), (3, -123452, '2024-03-09', '22:00', true), 
(1, -50000, '2024-02-07', '09:46', false), (2, 65499, '2024-04-04', '19:06', true), (3, 434000, '2024-05-11', '16:27', true), 
(1, 34000, '2024-05-29', '15:56', true), (2, -543987, '2024-02-24', '15:52', true), (3, 34512, '2024-06-22', '14:06', true), 
(3, -98000, '2024-07-12', '11:11', true), (3, 230000, '2024-08-02', '02:00', true), (4, -345000, '2024-07-24', '13:12', true), 
(3, -1234555, '2024-01-31', '04:43', false), (4, 1000001, '2024-09-08', '12:31', true);