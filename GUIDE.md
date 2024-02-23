# Online Scheduler Application Guide

This guide provides detailed instructions on how to interact with the Online Scheduler application for a car service agency. The application allows users to book, reschedule, and cancel appointments, as well as view all booked appointments and open slots for service operators.

## Getting Started

Before you start using the API, ensure you have the following installed:
- [Postman](https://www.postman.com/downloads/) for sending requests to the API.
- An SQL database setup as per the application's configuration (the application uses JDBC to interact with the database).

## API Overview

The application exposes the following endpoints:

1. **View All Booked Appointments**
   - Endpoint: GET /appointments
   - Description: Retrieves a list of all booked appointments.

2. **View Open Slots for Service Operators**
   - Endpoint: GET /{operatorId}/open-slots
   - Description: Retrieves a list of open slots for a specific service operator.

3. **Book an Appointment**
   - Endpoint: POST /appointments
   - Description: Books a new appointment.

4. **Reschedule an Appointment**
   - Endpoint: PUT /appointments/{appointmentId}
   - Description: Reschedules an existing appointment.

5. **Cancel an Appointment**
   - Endpoint: DELETE /appointments/{appointmentId}
   - Description: Cancels an existing appointment.

Below are the steps to interact with each endpoint using Postman.

## 1. View All Booked Appointments

**Steps:**
1. Open Postman and create a new request.
2. Set the method to GET.
3. Enter the request URL: `http://localhost:8080/appointments`.
4. Click Send.
5. The response will include a list of all booked appointments.

## 2. View Open Slots for Service Operators

**Steps:**
1. Open Postman and create a new request.
2. Set the method to GET.
3. Enter the request URL, replacing `{operatorId}` with the actual operator ID: `http://localhost:8080/{operatorId}/open-slots`.
4. Click Send.
5. The response will include a list of open slots for the specified service operator.

## 3. Book an Appointment

**Steps:**
1. Open Postman and create a new request.
2. Set the method to POST.
3. Enter the request URL: `http://localhost:8080/appointments`.
4. In the Body tab, select raw and choose JSON from the dropdown menu.
5. Enter the appointment details in JSON format.
6. Click Send.
7. The response will confirm the booking of the new appointment.

## 4. Reschedule an Appointment

**Steps:**
1. Open Postman and create a new request.
2. Set the method to PUT.
3. Enter the request URL, replacing `{appointmentId}` with the actual appointment ID: `http://localhost:8080/appointments/{appointmentId}`.
4. In the Body tab, select raw and choose JSON from the dropdown menu.
5. Enter the new appointment details in JSON format.
6. Click Send.
7. The response will confirm the rescheduling of the appointment.

## 5. Cancel an Appointment

**Steps:**
1. Open Postman and create a new request.
2. Set the method to DELETE.
3. Enter the request URL, replacing `{appointmentId}` with the actual appointment ID: `http://localhost:8080/appointments/{appointmentId}`.
4. Click Send.
5. The response will confirm the cancellation of the appointment.
