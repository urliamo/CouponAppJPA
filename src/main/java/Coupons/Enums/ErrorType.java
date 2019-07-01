package Coupons.Enums;

import java.sql.Date;
import java.time.LocalDateTime;

import Coupons.Utils.DateUtils;

public enum ErrorType {
GENERAL_ERROR(600, "General error"),
NAME_IS_ALREADY_EXISTS(600, "The name or mail you entered already exists"),
COMPANY_TYPE_NO_ID(600, "User is of type Company but with no CompanyID"),
COMPANY_ID_NOT_TYPE(600, "user has a company ID but is not Company type user"),
INVALID_NAME(600, "User does not exist with this name"),
INVALID_ID(600, "The ID you've enter is invalid"),
INVALID_AMOUNT(600,"The amount you've entered is invalid"),
EMPTY(600,"Empty value entered"),
USER_ID_DOES_NOT_EXIST(600,"no such userID in DB"),
COMPANY_ID_DOES_NOT_EXIST(600,"no such companyID in DB"),
CUSTOMER_ID_DOES_NOT_EXIST(600,"no such customerID in DB"),
COUPON_ID_DOES_NOT_EXIST(600,"no such couponID in DB"),
COMPANY_ID_MUST_BE_ASSIGNED(600,"attempt to add company with pre-existing ID"),
COMPANY_ID_ALREADY_EXIST(600,"company with this ID already exists"),
INVALID_PRICE(600,"The price you've entered is invalid"),
INVALID_EMAIL_OR_PASS(600,"The email or password you've entered is invalid."),
EXISTING_EMAIL(600,"The email you've entered is unavailable."),
INVALID_EMAIL(600,"The email you've entered is invalid."),
INVALID_PASSWORD(600,"The password you've entered is invalid."),
INVALID_DATES(600,"The dates you've entered is invalid."),
FIELD_IS_IRREPLACEABLE(600, "You can't change this field."),
NAME_IS_IRREPLACEABLE(600, "You can't change your name."),
COUPON_IS_OUT_OF_ORDER(600, "Coupon is out of order"),
EXISTING_COUPON_TITLE(600, "Coupon with this title already exists"),
EXISTING_COUPON_ID(600, "Coupon with this ID already exists"),
COUPON_ALREADY_EXPIRED(600, "coupon is past its' end-date"),
COUPON_DATE_MISMATCH(600, "coupon start date is after end date"),
LOGIN_FAILED(600, "Login failed. credentials incorrect."), 
USER_TYPE_MISMATCH(600, "user not authorized for this action (type or ID mismatch)"),
USER_ID_MISMATCH(600, "user ID does not match cached ID"),
COMPANY_ID_MISMATCH(600, "company ID does not match cached ID"),
USERNAME_DOES_NOT_EXISTS(600, "no such userName in DB"),
INVALID_CATEGORY(600, "invalid category"),
INVALID_IMAGE(600, "invalid image"), 
NOT_ENOUGH_COUPONS_IN_STOCK(600, "not enough coupons in stuck to fulfill purchase, amount is:"), 
PURCHASE_ID_DOES_NOT_EXIST(600,"no such purhcaseID in DB");

	
	private int internalErrorCode;
	private String internalMessage;
	
	private ErrorType(int internalErrorCode, String internalMessage) {
		this.internalErrorCode=internalErrorCode;
		this.internalMessage=internalMessage+LocalDateTime.now().toString();
	}

	public int getInternalErrorCode() {
		return internalErrorCode;
	}

	public String getInternalMessage() {
		return internalMessage;
	}
	
} 
