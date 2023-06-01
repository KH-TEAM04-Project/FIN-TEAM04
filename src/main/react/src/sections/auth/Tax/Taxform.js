import React, { useState } from 'react';
import './TaxForm.css';

function TaxForm() {

    const columnInfo = [
        { name: "lifeInsurance", label: "건강보험" },
        { name: "npension", label: "국민연금" },
        { name: "insurance", label: "보험료" },
        { name: "medi", label: "의료비" },
        { name: "edu", label: "교육비" },
        { name: "card", label: "신용카드" },
        { name: "dcard", label: "체크카드" },
        { name: "pension", label: "개인연금저축, 연금계좌" },
        { name: "cash", label: "현금영수증" },
        { name: "housefunds", label: "주택자금, 월세액" },
        { name: "housesaving", label: "주택마련저축" },
        { name: "invest", label: "장기집합투자증권저축, 벤처기업투자신탁" },
        { name: "sbo", label: "소기업, 소상공인 공제부금" },
        { name: "donation", label: "기부금" },
      ];
  const [deductionList, setDeductionList] = useState([]);
  
  const handleSubmit = (event) => {
    event.preventDefault();
    
    const form = event.target;
    const formData = new FormData(form);
    
    if (Array.from(formData.values()).some(value => value === '')) {
      alert('모든 분야를 기입해 주세요.');
      return;
    }

    setDeductionList((prev) => {
      return [...prev, Object.fromEntries(formData.entries())];
    });

    form.reset();
  };


  return (
    <div className="tax-form__container">
      <form onSubmit={handleSubmit}>
        <div className="tax-form__grid">
          {columnInfo.map(({ name, label }) => (
            <label key={name}>
              {label}:
              <input type="text" name={name} required />
            </label>
          ))}
        </div>
        <button type="submit">입력 내용 저장</button>
      </form>
      {deductionList.length ? (
        <table>
          <thead>
            <tr>
              {columnInfo.map(({ name, label }) => (
                <th key={name}>{label}</th>
              ))}
            </tr>
          </thead>
          <tbody>
            {deductionList.map((item, index) => (
              <tr key={index}>
                {columnInfo.map(({ name, label }) => (
                  <td key={name}>{item[name]}</td>
                ))}
              </tr>
            ))}
          </tbody>
        </table>
      ) : (
        <div>저장된 내용이 없습니다.</div>
      )}
    </div>
  );
}

export default TaxForm;
