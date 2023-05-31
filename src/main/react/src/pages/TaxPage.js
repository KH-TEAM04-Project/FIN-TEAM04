import React from 'react';
import TaxForm from '../sections/auth/Tax/Taxform';


function TaxPage() {
  return (
    <div>
      <h1>세금신고 페이지</h1>
      <p>아래의 폼을 입력해주세요:</p>
      <TaxForm />
    </div>
  );
}

export default TaxPage;
