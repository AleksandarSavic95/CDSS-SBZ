-- admin's password is "admin" (no quotes)
INSERT INTO user_table (id, role, name, username, password) VALUES (1, 'ADMIN', 'Admin Admin', 'admin', '$2a$10$b.T5EMJqJM/Y2XqbcBqs9.a.U6LFjfoWC/9iUhNd7iteaTC4.ney2');
-- doctors' passwords are all "123" (no quotes)
INSERT INTO user_table (id, role, name, username, password) VALUES (2, 'DOCTOR', 'Drago Dragic', 'drago', '$2a$10$7MhEaZ7h94V5yGznzE2jq.fKk3l9x/q6S6XJs1glPNJI5fwDQ/CDq');
INSERT INTO user_table (id, role, name, username, password) VALUES (3, 'DOCTOR', 'Marko Markovic', 'marko', '$2a$10$7MhEaZ7h94V5yGznzE2jq.fKk3l9x/q6S6XJs1glPNJI5fwDQ/CDq');
INSERT INTO user_table (id, role, name, username, password) VALUES (4, 'DOCTOR', 'Obrad Obradovic', 'obrad', '$2a$10$7MhEaZ7h94V5yGznzE2jq.fKk3l9x/q6S6XJs1glPNJI5fwDQ/CDq');
INSERT INTO doctor (id) VALUES (2);
INSERT INTO doctor (id) VALUES (3);
INSERT INTO doctor (id) VALUES (4);
-- --
INSERT INTO symptom (id, name) VALUES (1, 'Runny nose');
INSERT INTO symptom (id, name) VALUES (2, 'Sore throat');
INSERT INTO symptom (id, name) VALUES (3, 'Headache');
INSERT INTO symptom (id, name) VALUES (4, 'Sneezing');
INSERT INTO symptom (id, name) VALUES (5, 'Coughing');
INSERT INTO symptom (id, name) VALUES (6, 'temperature over 38');
INSERT INTO symptom (id, name) VALUES (7, 'Shivering');
INSERT INTO symptom (id, name) VALUES (8, 'Earache');
INSERT INTO symptom (id, name) VALUES (9, 'temperature between 40 and 41');
INSERT INTO symptom (id, name) VALUES (10, 'Loss of appetite');
INSERT INTO symptom (id, name) VALUES (11, 'Tiredness');
INSERT INTO symptom (id, name) VALUES (12, 'Nasal secretion');
INSERT INTO symptom (id, name) VALUES (13, 'Eye swelling');
INSERT INTO symptom (id, name) VALUES (14, 'Cold or Fever in the last 60 days');
INSERT INTO symptom (id, name) VALUES (15, '10 cases of high blood pressure in the last 6 months');
INSERT INTO symptom (id, name) VALUES (16, 'Frequent urination');
INSERT INTO symptom (id, name) VALUES (17, 'Weight loss');
INSERT INTO symptom (id, name) VALUES (18, 'Fatigue');
INSERT INTO symptom (id, name) VALUES (19, 'Nausea');
INSERT INTO symptom (id, name) VALUES (20, 'Nocturia');
INSERT INTO symptom (id, name) VALUES (21, 'Joint swelling');
INSERT INTO symptom (id, name) VALUES (22, 'Suffocation');
INSERT INTO symptom (id, name) VALUES (23, 'Chest pains');
INSERT INTO symptom (id, name) VALUES (24, 'Over 6 months hypertension');
INSERT INTO symptom (id, name) VALUES (25, 'Diabetes');
INSERT INTO symptom (id, name) VALUES (26, 'Surgery recovery');
INSERT INTO symptom (id, name) VALUES (27, 'Diarrhea');
INSERT INTO symptom (id, name) VALUES (28, 'Last 14 days with fever');
INSERT INTO symptom (id, name) VALUES (29, 'Last 21 days antibiotics');
-- --
INSERT INTO sickness (id, name, sickness_group) VALUES (1, 'Cold', 1);
INSERT INTO sickness (id, name, sickness_group) VALUES (2, 'Fever', 1);
INSERT INTO sickness (id, name, sickness_group) VALUES (3, 'Tonsillitis', 1);
INSERT INTO sickness (id, name, sickness_group) VALUES (4, 'Sinus infection', 1);
INSERT INTO sickness (id, name, sickness_group) VALUES (5, 'Hypertension', 2);
INSERT INTO sickness (id, name, sickness_group) VALUES (6, 'Diabetes', 2);
INSERT INTO sickness (id, name, sickness_group) VALUES (7, 'Chronic kidney disease', 3);
INSERT INTO sickness (id, name, sickness_group) VALUES (8, 'Acute kidney disease', 3);
INSERT INTO sickness (id, name, sickness_group) VALUES (9, 'High blood pressure', 4);
INSERT INTO sickness (id, name, sickness_group) VALUES (10, 'Recovering from surgery', 4);
-- --
INSERT INTO sickness_general_symptom (sickness_id, general_id) VALUES (1, 1);
INSERT INTO sickness_general_symptom (sickness_id, general_id) VALUES (1, 2);
INSERT INTO sickness_general_symptom (sickness_id, general_id) VALUES (1, 3);
INSERT INTO sickness_general_symptom (sickness_id, general_id) VALUES (1, 4);
INSERT INTO sickness_general_symptom (sickness_id, general_id) VALUES (1, 5);

INSERT INTO sickness_general_symptom (sickness_id, general_id) VALUES (2, 1);
INSERT INTO sickness_general_symptom (sickness_id, general_id) VALUES (2, 2);
INSERT INTO sickness_general_symptom (sickness_id, general_id) VALUES (2, 3);
INSERT INTO sickness_general_symptom (sickness_id, general_id) VALUES (2, 4);
INSERT INTO sickness_general_symptom (sickness_id, general_id) VALUES (2, 5);
INSERT INTO sickness_general_symptom (sickness_id, general_id) VALUES (2, 6);
INSERT INTO sickness_general_symptom (sickness_id, general_id) VALUES (2, 7);

INSERT INTO sickness_general_symptom (sickness_id, general_id) VALUES (3, 2);
INSERT INTO sickness_general_symptom (sickness_id, general_id) VALUES (3, 3);
INSERT INTO sickness_general_symptom (sickness_id, general_id) VALUES (3, 7);
INSERT INTO sickness_general_symptom (sickness_id, general_id) VALUES (3, 8);
INSERT INTO sickness_general_symptom (sickness_id, general_id) VALUES (3, 9);
INSERT INTO sickness_general_symptom (sickness_id, general_id) VALUES (3, 10);
INSERT INTO sickness_general_symptom (sickness_id, general_id) VALUES (3, 11);
INSERT INTO sickness_general_symptom (sickness_id, general_id) VALUES (3, 12);

INSERT INTO sickness_general_symptom (sickness_id, general_id) VALUES (4, 2);
INSERT INTO sickness_general_symptom (sickness_id, general_id) VALUES (4, 3);
INSERT INTO sickness_general_symptom (sickness_id, general_id) VALUES (4, 5);
INSERT INTO sickness_general_symptom (sickness_id, general_id) VALUES (4, 6);
INSERT INTO sickness_general_symptom (sickness_id, general_id) VALUES (4, 12);
INSERT INTO sickness_general_symptom (sickness_id, general_id) VALUES (4, 13);
INSERT INTO sickness_general_symptom (sickness_id, general_id) VALUES (4, 14);

INSERT INTO sickness_general_symptom (sickness_id, general_id) VALUES (5, 15);

INSERT INTO sickness_general_symptom (sickness_id, general_id) VALUES (6, 16);
INSERT INTO sickness_general_symptom (sickness_id, general_id) VALUES (6, 17);
INSERT INTO sickness_general_symptom (sickness_id, general_id) VALUES (6, 18);
INSERT INTO sickness_general_symptom (sickness_id, general_id) VALUES (6, 19);

INSERT INTO sickness_general_symptom (sickness_id, general_id) VALUES (7, 18);
INSERT INTO sickness_general_symptom (sickness_id, general_id) VALUES (7, 20);
INSERT INTO sickness_general_symptom (sickness_id, general_id) VALUES (7, 21);
INSERT INTO sickness_general_symptom (sickness_id, general_id) VALUES (7, 22);
INSERT INTO sickness_general_symptom (sickness_id, general_id) VALUES (7, 23);
INSERT INTO sickness_specific_symptom (sickness_id, specific_id) VALUES (7, 24);
INSERT INTO sickness_specific_symptom (sickness_id, specific_id) VALUES (7, 25);

INSERT INTO sickness_general_symptom (sickness_id, general_id) VALUES (8, 18);
INSERT INTO sickness_general_symptom (sickness_id, general_id) VALUES (8, 21);
INSERT INTO sickness_general_symptom (sickness_id, general_id) VALUES (8, 22);
INSERT INTO sickness_general_symptom (sickness_id, general_id) VALUES (8, 27);
INSERT INTO sickness_specific_symptom (sickness_id, specific_id) VALUES (8, 26);
INSERT INTO sickness_specific_symptom (sickness_id, specific_id) VALUES (8, 28);
INSERT INTO sickness_specific_symptom (sickness_id, specific_id) VALUES (8, 29);
-- -- -- MEDICINES -- -- --
INSERT INTO medicine (id, name, type) VALUES (1, 'ibuprofen', 'ANALGETIC');
INSERT INTO medicine (id, name, type) VALUES (2, 'paracetamol', 'ANTIBIOTIC');
-- -- -- INGREDIENTS -- -- --
INSERT INTO ingredient (id, name) VALUES (1, 'sorbitol');
INSERT INTO ingredient (id, name) VALUES (2, 'vitamin C');
INSERT INTO ingredient (id, name) VALUES (3, 'ascorbic acid');
-- --
INSERT INTO medicine_ingredient (medicine_id, ingredient_id) VALUES (1, 2);
INSERT INTO medicine_ingredient (medicine_id, ingredient_id) VALUES (1, 3);

INSERT INTO medicine_ingredient (medicine_id, ingredient_id) VALUES (2, 1);
-- -- -- PATIENTS -- -- --
INSERT INTO patient (id, medical_card_number, name) VALUES (1, 'p-1111', 'Milivoje Radomir');
INSERT INTO patient (id, medical_card_number, name) VALUES (2, 'p-2222', 'Ljubivoje Rsumovic');
INSERT INTO patient (id, medical_card_number, name) VALUES (3, 'p-3333', 'Jegdomir Slavoljub');
-- --
INSERT INTO patient_sickness (patient_id, sickness_id) VALUES (1, 6);
INSERT INTO patient_sickness (patient_id, sickness_id) VALUES (1, 10);

INSERT INTO patient_sickness (patient_id, sickness_id) VALUES (2, 3);
-- -- -- ALLERGENS -- -- --
INSERT INTO allergen_medicine (patient_id, medicine_id) VALUES (1, 1);
INSERT INTO allergen_medicine (patient_id, medicine_id) VALUES (1, 2);
INSERT INTO allergen_medicine (patient_id, medicine_id) VALUES (2, 2);
-- --
INSERT INTO allergen_ingredient (patient_id, ingredient_id) VALUES (2, 1);
INSERT INTO allergen_ingredient (patient_id, ingredient_id) VALUES (3, 1);
-- -- -- TREATMENTS -- -- --
INSERT INTO treatment (id, timestamp, sickness_id, doctor_id, patient_id) VALUES (1, '2018-06-14 19:44:52', 5, 2, 1);
INSERT INTO treatment (id, timestamp, sickness_id, doctor_id, patient_id) VALUES (2, '2018-06-14 19:44:52', 2, 2, 1);
INSERT INTO treatment (id, timestamp, sickness_id, doctor_id, patient_id) VALUES (3, '2018-06-14 19:44:52', 9, 3, 1);
INSERT INTO treatment (id, timestamp, sickness_id, doctor_id, patient_id) VALUES (4, '2018-06-14 19:44:52', 9, 3, 1);
INSERT INTO treatment (id, timestamp, sickness_id, doctor_id, patient_id) VALUES (5, '2018-06-14 19:44:52', 9, 4, 1);
INSERT INTO treatment (id, timestamp, sickness_id, doctor_id, patient_id) VALUES (6, '2018-06-14 19:44:52', 9, 4, 1);
-- patient[id=1] had 7 cases of highBloodPressure[id=9]
INSERT INTO treatment (id, timestamp, sickness_id, doctor_id, patient_id) VALUES (7, '2018-09-04 11:41:52', 9, 2, 1);
INSERT INTO treatment (id, timestamp, sickness_id, doctor_id, patient_id) VALUES (8, '2018-09-04 11:42:52', 9, 2, 1);
INSERT INTO treatment (id, timestamp, sickness_id, doctor_id, patient_id) VALUES (9, '2018-09-04 11:43:52', 9, 2, 1);
INSERT INTO treatment (id, timestamp, sickness_id, doctor_id, patient_id) VALUES (10, '2018-09-04 11:44:52', 9, 2, 1);
INSERT INTO treatment (id, timestamp, sickness_id, doctor_id, patient_id) VALUES (11, '2018-09-04 11:45:52', 9, 2, 1);
INSERT INTO treatment (id, timestamp, sickness_id, doctor_id, patient_id) VALUES (12, '2018-09-04 11:46:52', 9, 2, 1);
INSERT INTO treatment (id, timestamp, sickness_id, doctor_id, patient_id) VALUES (13, '2018-09-04 11:47:52', 9, 2, 1);
-- --  MEDICINES  -- --
INSERT INTO treatment_medicine (treatment_id, medicine_id) VALUES (1, 1);
INSERT INTO treatment_medicine (treatment_id, medicine_id) VALUES (2, 1);
INSERT INTO treatment_medicine (treatment_id, medicine_id) VALUES (3, 1);
INSERT INTO treatment_medicine (treatment_id, medicine_id) VALUES (4, 1);
INSERT INTO treatment_medicine (treatment_id, medicine_id) VALUES (5, 1);
INSERT INTO treatment_medicine (treatment_id, medicine_id) VALUES (6, 1);
INSERT INTO treatment_medicine (treatment_id, medicine_id) VALUES (7, 1);
INSERT INTO treatment_medicine (treatment_id, medicine_id) VALUES (8, 2);
INSERT INTO treatment_medicine (treatment_id, medicine_id) VALUES (9, 2);
INSERT INTO treatment_medicine (treatment_id, medicine_id) VALUES (10, 2);
INSERT INTO treatment_medicine (treatment_id, medicine_id) VALUES (11, 2);
INSERT INTO treatment_medicine (treatment_id, medicine_id) VALUES (12, 2);