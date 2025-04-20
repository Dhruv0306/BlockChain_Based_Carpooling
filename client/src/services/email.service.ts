import { useError } from '../contexts/ErrorContext';
import { useLoading } from '../contexts/LoadingContext';
import axios from 'axios';

interface EmailTemplates {
  BOOKING_CONFIRMATION: string;
  RIDE_UPDATED: string;
  RIDE_CANCELLED: string;
  DRIVER_ASSIGNED: string;
  PAYMENT_RECEIVED: string;
  RIDE_COMPLETED: string;
  RATING_REMINDER: string;
}

class EmailService {
  private static readonly API_URL = '/api/email';
  private static readonly TEMPLATES: EmailTemplates = {
    BOOKING_CONFIRMATION: 'booking-confirmation',
    RIDE_UPDATED: 'ride-updated',
    RIDE_CANCELLED: 'ride-cancelled',
    DRIVER_ASSIGNED: 'driver-assigned',
    PAYMENT_RECEIVED: 'payment-received',
    RIDE_COMPLETED: 'ride-completed',
    RATING_REMINDER: 'rating-reminder'
  };

  private static async sendEmail(templateName: string, data: any, recipientEmail: string) {
    try {
      await axios.post(`${this.API_URL}/send`, {
        template: templateName,
        data,
        to: recipientEmail
      });
    } catch (error) {
      console.error('Failed to send email:', error);
      throw error;
    }
  }

  public static async sendBookingConfirmation(rideDetails: any, userEmail: string) {
    return this.sendEmail(
      this.TEMPLATES.BOOKING_CONFIRMATION,
      rideDetails,
      userEmail
    );
  }

  public static async sendRideUpdateNotification(rideDetails: any, userEmail: string) {
    return this.sendEmail(
      this.TEMPLATES.RIDE_UPDATED,
      rideDetails,
      userEmail
    );
  }

  public static async sendRideCancellationNotification(rideDetails: any, userEmail: string) {
    return this.sendEmail(
      this.TEMPLATES.RIDE_CANCELLED,
      rideDetails,
      userEmail
    );
  }

  public static async sendDriverAssignedNotification(driverDetails: any, userEmail: string) {
    return this.sendEmail(
      this.TEMPLATES.DRIVER_ASSIGNED,
      driverDetails,
      userEmail
    );
  }

  public static async sendPaymentConfirmation(paymentDetails: any, userEmail: string) {
    return this.sendEmail(
      this.TEMPLATES.PAYMENT_RECEIVED,
      paymentDetails,
      userEmail
    );
  }

  public static async sendRideCompletionNotification(rideDetails: any, userEmail: string) {
    return this.sendEmail(
      this.TEMPLATES.RIDE_COMPLETED,
      rideDetails,
      userEmail
    );
  }

  public static async sendRatingReminder(rideDetails: any, userEmail: string) {
    return this.sendEmail(
      this.TEMPLATES.RATING_REMINDER,
      rideDetails,
      userEmail
    );
  }
}

// Hook to use email service with loading and error handling
export const useEmailService = () => {
  const { showLoading, hideLoading } = useLoading();
  const { handleError } = useError();

  const sendEmailWithHandling = async (
    emailFunction: (...args: any[]) => Promise<void>,
    ...args: any[]
  ) => {
    try {
      showLoading();
      await emailFunction(...args);
    } catch (error) {
      handleError(error);
    } finally {
      hideLoading();
    }
  };

  return {
    sendBookingConfirmation: (rideDetails: any, userEmail: string) =>
      sendEmailWithHandling(EmailService.sendBookingConfirmation, rideDetails, userEmail),
    sendRideUpdateNotification: (rideDetails: any, userEmail: string) =>
      sendEmailWithHandling(EmailService.sendRideUpdateNotification, rideDetails, userEmail),
    sendRideCancellationNotification: (rideDetails: any, userEmail: string) =>
      sendEmailWithHandling(EmailService.sendRideCancellationNotification, rideDetails, userEmail),
    sendDriverAssignedNotification: (driverDetails: any, userEmail: string) =>
      sendEmailWithHandling(EmailService.sendDriverAssignedNotification, driverDetails, userEmail),
    sendPaymentConfirmation: (paymentDetails: any, userEmail: string) =>
      sendEmailWithHandling(EmailService.sendPaymentConfirmation, paymentDetails, userEmail),
    sendRideCompletionNotification: (rideDetails: any, userEmail: string) =>
      sendEmailWithHandling(EmailService.sendRideCompletionNotification, rideDetails, userEmail),
    sendRatingReminder: (rideDetails: any, userEmail: string) =>
      sendEmailWithHandling(EmailService.sendRatingReminder, rideDetails, userEmail)
  };
};
