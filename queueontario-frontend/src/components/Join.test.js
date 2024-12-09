import React from "react";
import { render, fireEvent, screen, waitFor } from "@testing-library/react";
import { BrowserRouter as Router } from "react-router-dom";
import JoinWaitList from "./Join";

//mock useNavigate and useLocation
const mockNavigate = jest.fn();

jest.mock("react-router-dom", () => ({
  ...jest.requireActual("react-router-dom"),
  useLocation: () => ({ state: { centerId: 123 } }),
  useNavigate: () => mockNavigate,
}));

//mock global fetch
global.fetch = jest.fn();

beforeEach(() => {
  const mockUserInfo = JSON.stringify({ id: 1 });
  window.localStorage.setItem("userInfo", mockUserInfo);
});

afterEach(() => {
  jest.clearAllMocks();
  jest.clearAllTimers();
  window.localStorage.clear();
});

describe("JoinWaitList Component", () => {
  test("renders form elements correctly", () => {
    render(
      <Router>
        <JoinWaitList />
      </Router>
    );

    expect(screen.getByLabelText(/Service:/i)).toBeInTheDocument();
    expect(screen.getByLabelText(/Email Address:/i)).toBeInTheDocument();
    expect(screen.getByLabelText(/Name:/i)).toBeInTheDocument();
    expect(screen.getByLabelText(/Phone Number:/i)).toBeInTheDocument();
    expect(screen.getByRole("button", { name: /Join Waitlist/i })).toBeInTheDocument();
  });

  test("handles input changes", () => {
    render(
      <Router>
        <JoinWaitList />
      </Router>
    );

    const serviceInput = screen.getByLabelText(/Service:/i);
    const emailInput = screen.getByLabelText(/Email Address:/i);
    const nameInput = screen.getByLabelText(/Name:/i);
    const phoneInput = screen.getByLabelText(/Phone Number:/i);

    fireEvent.change(serviceInput, { target: { value: "HealthCardRenewal" } });
    fireEvent.change(emailInput, { target: { value: "test@example.com" } });
    fireEvent.change(nameInput, { target: { value: "John Doe" } });
    fireEvent.change(phoneInput, { target: { value: "1234567890" } });

    expect(serviceInput.value).toBe("HealthCardRenewal");
    expect(emailInput.value).toBe("test@example.com");
    expect(nameInput.value).toBe("John Doe");
    expect(phoneInput.value).toBe("1234567890");
  });

  test("shows error if user is not logged in", () => {
    window.localStorage.clear(); 

    render(
      <Router>
        <JoinWaitList />
      </Router>
    );

    expect(
      screen.getByText(/Please log in to join the waitlist./i)
    ).toBeInTheDocument();
  });

  test("handles failed submission and displays error message", async () => {
    global.fetch.mockResolvedValueOnce({
      ok: false,
      status: 500, 
    });
  
    render(
      <Router>
        <JoinWaitList />
      </Router>
    );
  
    fireEvent.change(screen.getByLabelText(/Service:/i), {
      target: { value: "HealthCardRenewal" },
    });
    fireEvent.change(screen.getByLabelText(/Email Address:/i), {
      target: { value: "test@example.com" },
    });
    fireEvent.change(screen.getByLabelText(/Name:/i), {
      target: { value: "John Doe" },
    });
    fireEvent.change(screen.getByLabelText(/Phone Number:/i), {
      target: { value: "1234567890" },
    });
  
    fireEvent.click(screen.getByRole("button", { name: /Join Waitlist/i }));
  
    expect(await screen.findByText(/Failed to join the waitlist. Please try again./i)).toBeInTheDocument();
  
    expect(mockNavigate).not.toHaveBeenCalled();
  });
  
});
