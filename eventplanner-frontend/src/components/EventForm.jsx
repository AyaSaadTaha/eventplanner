import { useState, useEffect } from 'react';
import { TextField, Button, Box, Typography } from '@mui/material';
import { DatePicker } from '@mui/x-date-pickers';
import { createEvent, updateEvent } from '../services/eventService';
import dayjs from 'dayjs';

export default function EventForm({ event, onSuccess }) {
    const [formData, setFormData] = useState({
        name: '',
        date: dayjs().add(1, 'day'),
        location: '',
        description: ''
    });

    useEffect(() => {
        if (event) {
            setFormData({
                name: event.name,
                date: dayjs(event.date),
                location: event.location,
                description: event.description || ''
            });
        }
    }, [event]);

    const handleChange = (e) => {
        setFormData({
            ...formData,
            [e.target.name]: e.target.value
        });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const payload = {
                ...formData,
                date: formData.date.format('YYYY-MM-DD')
            };

            if (event) {
                await updateEvent(event.id, payload);
            } else {
                await createEvent(payload);
            }
            onSuccess();
            setFormData({
                name: '',
                date: dayjs().add(1, 'day'),
                location: '',
                description: ''
            });
        } catch (error) {
            console.error('Error saving event:', error);
        }
    };

    return (
        <Box component="form" onSubmit={handleSubmit} sx={{ mb: 3 }}>
            <Typography variant="h6" gutterBottom>
                {event ? 'Edit Event' : 'Create New Event'}
            </Typography>
            <TextField
                name="name"
                label="Event Name"
                value={formData.name}
                onChange={handleChange}
                fullWidth
                margin="normal"
                required
            />
            <DatePicker
                label="Event Date"
                value={formData.date}
                onChange={(newDate) => setFormData({...formData, date: newDate})}
                minDate={dayjs().add(1, 'day')}
                sx={{ mt: 2, width: '100%' }}
            />
            <TextField
                name="location"
                label="Location"
                value={formData.location}
                onChange={handleChange}
                fullWidth
                margin="normal"
                required
            />
            <TextField
                name="description"
                label="Description"
                value={formData.description}
                onChange={handleChange}
                fullWidth
                margin="normal"
                multiline
                rows={4}
            />
            <Button type="submit" variant="contained" sx={{ mt: 2 }}>
                {event ? 'Update' : 'Create'}
            </Button>
            {event && (
                <Button
                    variant="outlined"
                    sx={{ mt: 2, ml: 2 }}
                    onClick={() => onSuccess()}
                >
                    Cancel
                </Button>
            )}
        </Box>
    );
}