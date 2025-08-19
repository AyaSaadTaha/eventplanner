import { useState } from 'react';
import { Button, MenuItem, Select, Dialog, DialogTitle, DialogContent, DialogActions } from '@mui/material';
import { getEvents, registerParticipant } from '../services/eventService.js';
import {getEventParticipants } from '../services/participantService.js';

export default function ParticipantActions({ participantId }) {
    const [events, setEvents] = useState([]);
    const [selectedEvent, setSelectedEvent] = useState('');
    const [openDialog, setOpenDialog] = useState(false);
    const [participantEvents, setParticipantEvents] = useState([]);
    const [showEvents, setShowEvents] = useState(false);

    const fetchEvents = async () => {
        const response = await getEvents();
        setEvents(response.data);
    };

    const handleAddToEvent = async () => {
        await registerParticipant(selectedEvent, participantId);
        setOpenDialog(false);
        setSelectedEvent('');
    };

    const handleShowEvents = async () => {
        const response = await getEventParticipants(participantId);
        setParticipantEvents(response.data);
        setShowEvents(true);
    };

    return (
        <div style={{ marginTop: '20px' }}>
            <Button
                variant="contained"
                onClick={() => { fetchEvents(); setOpenDialog(true); }}
                style={{ marginRight: '10px' }}
            >
                Add to Event
            </Button>

            <Button
                variant="outlined"
                onClick={handleShowEvents}
            >
                Show Participant's Events
            </Button>

            {/* Dialog for adding to event */}
            <Dialog open={openDialog} onClose={() => setOpenDialog(false)}>
                <DialogTitle>Add to Event</DialogTitle>
                <DialogContent>
                    <Select
                        value={selectedEvent}
                        onChange={(e) => setSelectedEvent(e.target.value)}
                        fullWidth
                        style={{ marginTop: '20px' }}
                    >
                        {events.map(event => (
                            <MenuItem key={event.id} value={event.id}>
                                {event.name} ({new Date(event.date).toLocaleDateString()})
                            </MenuItem>
                        ))}
                    </Select>
                </DialogContent>
                <DialogActions>
                    <Button onClick={() => setOpenDialog(false)}>Cancel</Button>
                    <Button onClick={handleAddToEvent} disabled={!selectedEvent}>Save</Button>
                </DialogActions>
            </Dialog>

            {/* Dialog for showing events */}
            <Dialog open={showEvents} onClose={() => setShowEvents(false)}>
                <DialogTitle>Participant's Events</DialogTitle>
                <DialogContent>
                    {participantEvents.length > 0 ? (
                        <ul>
                            {participantEvents.map(event => (
                                <li key={event.id}>
                                    {event.name} - {new Date(event.date).toLocaleDateString()}
                                </li>
                            ))}
                        </ul>
                    ) : (
                        <p>No events found for this participant</p>
                    )}
                </DialogContent>
                <DialogActions>
                    <Button onClick={() => setShowEvents(false)}>Close</Button>
                </DialogActions>
            </Dialog>
        </div>
    );
}