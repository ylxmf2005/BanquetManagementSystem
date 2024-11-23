// src/validationSchemas.ts

import * as Yup from 'yup';

// Attendee validation schema
export const attendeeSchema = Yup.object().shape({
    firstName: Yup.string()
        .required('This field is required.')
        .matches(/^[A-Za-z]+$/, 'First name must contain only English letters.'),
    lastName: Yup.string()
        .required('This field is required.')
        .matches(/^[A-Za-z]+$/, 'Last name must contain only English letters.'),
    email: Yup.string()
        .required('This field is required.')
        .email('Invalid email format.'),
    address: Yup.string()
        .required('This field is required.'),
    type: Yup.string()
        .required('This field is required.'),
    organization: Yup.string()
        .required('This field is required.'),
    mobileNo: Yup.string()
        .required('This field is required.')
        .matches(/^\d{8}$/, 'Mobile number must be exactly 8 digits.'),
    password: Yup.string(),
});

// Registration validation schema
export const registrationSchema = Yup.object().shape({
    seatNo: Yup.number()
        .required('Seat number is required.')
        .positive('Seat number must be a positive integer.')
        .integer('Seat number must be a positive integer.'),
    drinkChoice: Yup.string()
        .required('This field is required.'),
    mealChoice: Yup.string()
        .required('This field is required.'),
    remarks: Yup.string(),
});
// Define the Meal validation schema
const mealSchema = Yup.object().shape({
    type: Yup.string().required('Meal Type is required'),
    dishName: Yup.string().required('Dish Name is required'),
    price: Yup.number()
        .typeError('Price must be a number')
        .required('Price is required')
        .min(0, 'Price cannot be negative'),
    specialCuisine: Yup.string().required('Special Cuisine is required'),
});

// Define the Banquet validation schema
export const banquetSchema = Yup.object().shape({
    name: Yup.string().required('Banquet Name is required'),
    dateTime: Yup.string().required('Date & Time is required'),
    address: Yup.string().required('Address is required'),
    location: Yup.string().required('Location is required'),
    contactFirstName: Yup.string().required('Contact First Name is required'),
    contactLastName: Yup.string().required('Contact Last Name is required'),
    available: Yup.string()
        .required('Available is required')
        .oneOf(['Y', 'N'], 'Available must be "Y" or "N"'),
    quota: Yup.number()
        .typeError('Quota must be a number')
        .required('Quota is required')
        .integer('Quota must be an integer')
        .min(0, 'Quota cannot be negative'),
    meals: Yup.array()
        .of(mealSchema)
        .min(1, 'At least one meal is required')
        .required('Meals are required'),
});