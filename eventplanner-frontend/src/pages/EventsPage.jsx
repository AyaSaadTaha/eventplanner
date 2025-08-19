import { useState, useEffect } from 'react';
import { getEvents, deleteEvent } from '../services/eventService';
import EventList from '../components/EventList';
import EventForm from '../components/EventForm';
import { Button, Box } from '@mui/material';

export default function EventsPage() {
    const [events, setEvents] = useState([]);
    const [editingEvent, setEditingEvent] = useState(null);

    const fetchEvents = async () => {
        try {
            const { data } = await getEvents();
            setEvents(data);
        } catch (error) {
            console.error('Failed to fetch events:', error);
        }
    };

    useEffect(() => {
        fetchEvents();
    }, []);

    const handleDelete = async (id) => {
        try {
            await deleteEvent(id);
            fetchEvents();
        } catch (error) {
            console.error('Failed to delete event:', error);
        }
    };

    return (
        <Box sx={{ p: 3 }}>
            <h1>Events Management</h1>
            <EventForm
                event={editingEvent}
                onSuccess={() => {
                    setEditingEvent(null);
                    fetchEvents();
                }}
            />
            <EventList
                events={events}
                onEdit={setEditingEvent}
                onDelete={handleDelete}
            />
        </Box>
    );
}