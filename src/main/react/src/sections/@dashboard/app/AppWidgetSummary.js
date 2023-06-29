import PropTypes from 'prop-types';
import { Card, Typography } from '@mui/material';

// ...

AppWidgetSummary.propTypes = {
  color: PropTypes.string,
  title: PropTypes.string.isRequired,
  total: PropTypes.number.isRequired,
  sx: PropTypes.object,
};

export default function AppWidgetSummary({ title, total, color = 'primary', sx, ...other }) {
  const formattedTotal = total !== null ? total.toLocaleString() : '';

  return (
    <Card
      sx={{
        py: 3,
        boxShadow: 0,
        textAlign: 'center',
        color: (theme) => theme.palette[color].darker,
        bgcolor: (theme) => theme.palette[color].lighter,
        ...sx,
      }}
      {...other}
    >
      <Typography variant="h3" sx={{ overflowWrap: 'break-word' }}>
        {formattedTotal}
      </Typography>
      <Typography variant="subtitle2" sx={{ opacity: 0.72 }}>
        {title}
      </Typography>
    </Card>
  );
}
