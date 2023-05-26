import PropTypes from 'prop-types';
import { forwardRef } from 'react';
// @mui
import { Box } from '@mui/material';

// ----------------------------------------------------------------------
const token = localStorage.getItem('accessToken');
const sub = token ? JSON.parse(atob(token.split('.')[1])).sub : '';
console.log(sub);

const SvgColor = forwardRef(({ src, sx, ...other }, sub) => (
  <Box
    component="span"
    className="svg-color"
    ref={sub}
    sx={{
      width: 24,
      height: 24,
      display: 'inline-block',
      bgcolor: 'currentColor',
      mask: `url(${src}) no-repeat center / contain`,
      WebkitMask: `url(${src}) no-repeat center / contain`,
      ...sx,
    }}
    {...other}
  />
));

SvgColor.propTypes = {
  src: PropTypes.string,
  sx: PropTypes.object,
};

export default SvgColor;
