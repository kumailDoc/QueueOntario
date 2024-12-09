import React from 'react';
import { render, screen, fireEvent, act } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom'; 
import Issues from './Issues';

//Replicate localStorage
beforeEach(() => {
  localStorage.setItem('userInfo', JSON.stringify({ roles: ['ROLE_ADMIN'], username: 'adminUser' }));
  jest.spyOn(window, 'alert').mockImplementation(() => {}); 
  jest.spyOn(global, 'fetch'); 
});

afterEach(() => {
  localStorage.clear();
  jest.restoreAllMocks(); 
});

describe('Issues Component', () => {

  test('renders "Reported Issues" heading', async () => {
    await act(async () => {
      render(
        <MemoryRouter>
          <Issues />
        </MemoryRouter>
      );
    });

    const headerElement = screen.getByText(/Reported Issues/i);
    expect(headerElement).toBeInTheDocument();
  });

  test('displays message when there are no issues', async () => {
    //Mock fetch to return an empty list of issues
    global.fetch.mockResolvedValueOnce({
      ok: true,
      json: async () => [],
    });

    await act(async () => {
      render(
        <MemoryRouter>
          <Issues />
        </MemoryRouter>
      );
    });

    const noIssuesMessage = await screen.findByText(/No issues to display/i); // use findByText for async elements
    expect(noIssuesMessage).toBeInTheDocument();
  });

  test('displays error message if user does not have permission', async () => {
    //Set user with no permissions (not the admin)
    localStorage.setItem('userInfo', JSON.stringify({ roles: ['ROLE_USER'], username: 'regularUser' }));

    await act(async () => {
      render(
        <MemoryRouter>
          <Issues />
        </MemoryRouter>
      );
    });

    const errorElement = await screen.findByText(/You do not have permission to view these issues/i);
    expect(errorElement).toBeInTheDocument();
  });

  test('displays issues when available', async () => {
    const mockIssues = [
      { id: 1, name: 'Issue 1', comments: 'This is a test comment', email: 'test1@example.com', createdAt: '2024-12-05T10:00:00Z' },
      { id: 2, name: 'Issue 2', comments: 'Another comment', email: 'test2@example.com', createdAt: '2024-12-06T10:00:00Z' }
    ];

    // Mock API response to return issues
    global.fetch.mockResolvedValueOnce({
      ok: true,
      json: async () => mockIssues,
    });

    await act(async () => {
      render(
        <MemoryRouter>
          <Issues />
        </MemoryRouter>
      );
    });

    const issueTitle1 = await screen.findByText(/Issue 1/i); 
    const issueTitle2 = await screen.findByText(/Issue 2/i);

    expect(issueTitle1).toBeInTheDocument();
    expect(issueTitle2).toBeInTheDocument();
  });

  test('deletes an issue and updates the list', async () => {
    const mockIssues = [
      { id: 1, name: 'Issue 1', comments: 'This is a test comment', email: 'test1@example.com', createdAt: '2024-12-05T10:00:00Z' },
      { id: 2, name: 'Issue 2', comments: 'Another comment', email: 'test2@example.com', createdAt: '2024-12-06T10:00:00Z' }
    ];

    //Mock API response to return issues and DELETE request
    global.fetch
      .mockResolvedValueOnce({
        ok: true,
        json: async () => mockIssues,
      })
      .mockResolvedValueOnce({ ok: true }); 

    await act(async () => {
      render(
        <MemoryRouter>
          <Issues />
        </MemoryRouter>
      );
    });

    // Wait for delete button to be available
    const deleteButtons = await screen.findAllByText(/Delete/i);
    
    // Simulate clicking the "Delete" button
    await act(async () => {
      fireEvent.click(deleteButtons[0]);
    });

    // Check that alert is called (mocked)
    expect(window.alert).toHaveBeenCalledWith('Issue deleted successfully!');
  });

});
