// api.js (or any desired file)
const authorizedFetch = async (url, accessToken, options) => {
    options = options || {};
    options.headers = options.headers || {};
    options.headers['Authorization'] = `Bearer ${accessToken}`;
  
    const response = await fetch(url, options);
    return response.json();
  };
  export default authorizedFetch;
  