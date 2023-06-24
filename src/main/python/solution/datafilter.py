import numpy as np
import re


data = np.empty((0, 9), str)
# 임시 데이터 셋
# data = np.append(data, [['엔에이치엔페이코', 'PAYCO 포인트 카드', '국내 어디서나', '최대1%', '이달의 브랜드', '최대10%', '발급즉시',  '5,000원',  'https://api.card-gorilla.com:8080/storage/card/2310/card_img/23676/2310card.png'], ['우리카드', 'NU 오하쳌(오늘하루체크)', '온라인 쇼핑', '5%', '간편결제', '5%', '배달/주문', '5%',  'https://api.card-gorilla.com:8080/storage/card/762/card_img/26507/762card.png'], ['KB국민카드', '노리2 체크카드(KB Pay)', '커피, 모바일, 문화', '10%', '뷰티, 편의점', '5%', 'KB Pay',  '2%',  'https://api.card-gorilla.com:8080/storage/card/2422/card_img/27141/2422card.png'], ['신한카드', '신한카드 Deep Dream 체크', '모두드림', '0.2%', '더해드림', '0.6%', '챙겨드림', '1.0%',  'https://api.card-gorilla.com:8080/storage/card/281/card_img/20503/281card.png'], ['우리카드', '010PAY 체크카드', '결제할 때마다', '최대 0.4%', '매월 3번 응DAY엔', '3.3%',  '휴대폰 소액결제로', '최대 20만원',  'https://api.card-gorilla.com:8080/storage/card/765/card_img/25707/765card.png'], ['KB국민카드', '노리체크카드', '대중교통', '10%', '이동통신요금', '2,500원', 'CGV', '35%',  'https://api.card-gorilla.com:8080/storage/card/348/card_img/20581/348card.png'], ['신한카드', '신한카드 플리 체크(산리오캐릭터즈)', '국내 이용금액', '최대 0.3%', '해외 이용금액', '0.2%',  '매 월 2회', '할인쿠폰',  'https://api.card-gorilla.com:8080/storage/card/2492/card_img/28476/2492card.png'], ['한패스', '트리플카드', '해외 결제시', '3%', '국내 결제시', '0.3%', '전 세계 ATM', '무료',  'https://api.card-gorilla.com:8080/storage/card/2454/card_img/27870/2454card.png'], ['우리카드', '네이버페이 우리카드 체크', '네이버페이', '1%', '공항라운지', '무료', '해외수수료', '면제',  'https://api.card-gorilla.com:8080/storage/card/588/card_img/21345/588card.png'], ['KB국민카드', '노리2 체크카드(Global)', '커피,모바일,문화', '10%', '해외 가맹점', '2%', '공항라운지',  '연 1회',  'https://api.card-gorilla.com:8080/storage/card/2423/card_img/27142/2423card.png'], ['신한카드', '신한카드 Hey Young 체크', '대중교통', '5%', '영화', '5천원', '간편결제', '1%',  'https://api.card-gorilla.com:8080/storage/card/618/card_img/21385/618card.png'], ['우리카드', '카드의정석 COOKIE CHECK', '전세계 공항라운지', '무료입장', '해외가맹점', '2~1%', '영화',  '5,000원',  'https://api.card-gorilla.com:8080/storage/card/312/card_img/20557/312card.png'], ['하나카드', '네이버페이 머니 하나 체크카드', '네이버페이', '1.2%', '', '', '', '',  'https://api.card-gorilla.com:8080/storage/card/2432/card_img/27295/2432card.png'], ['신한카드', '신한카드 Deep Dream 체크(미니언즈)', '모두드림', '0.2%', '더해드림', '0.6%', '챙겨드림',  '1.0%',  'https://api.card-gorilla.com:8080/storage/card/646/card_img/21410/646card.png'], ['토스뱅크', '토스뱅크카드', '대중교통', '500원', '커피', '500원', '편의점', '500원',  'https://api.card-gorilla.com:8080/storage/card/2269/card_img/20697/2269card.png'], ['KB국민카드', '카카오페이 KB국민 체크카드', '카카오페이 결제', '2%', '커피전문점', '1%', '대중교통', '1%',  'https://api.card-gorilla.com:8080/storage/card/332/card_img/20576/332card.png'], ['카카오뱅크', '카카오뱅크 프렌즈 체크카드', '국내외 가맹점', '0.2~0.4%', '병원 업종', '1만원', '배달의민족',  '2천원',  'https://api.card-gorilla.com:8080/storage/card/435/card_img/20935/435card.png'], ['KB국민카드', '위글위글 첵첵 체크카드', '네이버페이,카카오페이', '2~4천원', '스타벅스', '2~4천원', '텐바이텐',  '2~4천원',  'https://api.card-gorilla.com:8080/storage/card/614/card_img/21381/614card.png'], ['NH농협카드', 'NH20해봄체크카드', '온라인쇼핑몰', '5%', '배달앱', '5%', '커피', '20%',  'https://api.card-gorilla.com:8080/storage/card/362/card_img/20603/362card.png'], ['하나카드', '트래블로그 체크카드', '해외 가맹점 이용', '수수료', '해외 ATM 인출', '수수료', '국내가맹점', '0.3%',  'https://api.card-gorilla.com:8080/storage/card/2394/card_img/25975/2394card.png'], ['신한카드', '네이버페이 신한카드 체크 S2', '네이버 최대', '2%', '네이버 외', '0.1%', '', '',  'https://api.card-gorilla.com:8080/storage/card/2268/card_img/20698/2268card.png'], ['NH농협카드', '올바른POINT체크카드', '전 가맹점', '0.4~0.2%', '해외', '0.3%', '국제공항라운지', '무료',  'https://api.card-gorilla.com:8080/storage/card/360/card_img/25852/360card.png'], ['신한카드', '알뜰교통카드 S20 체크', '전국 지하철/버스/택시', '10%', '이동통신요금 최대', '3천원', '커피전문점',  '20%',  'https://api.card-gorilla.com:8080/storage/card/743/card_img/28952/743card.png'], ['신한카드', '카카오페이 신한 콘 체크카드', '10회결제 때마다', '최대2만원', '카카오톡 주문하기', '최대6천원',  '해외가맹점', '1%',  'https://api.card-gorilla.com:8080/storage/card/670/card_img/21435/670card.png'], ['신한카드', '쿠팡 신한카드 체크', '쿠팡캐시', '3%', '스타벅스', '10%', '', '',  'https://api.card-gorilla.com:8080/storage/card/283/card_img/20505/283card.png'], ['신한카드', '신한카드 On 체크(잔망루피)', '간편결제', '2%', '생활편의', '2%', '해외 이용금액', '1.3%',  'https://api.card-gorilla.com:8080/storage/card/2379/card_img/25379/2379card.png'], ['KB국민카드', '나라사랑체크카드', 'P.X / GS25해군마트 최대', '20%', '대중교통', '20%', '영화', '35%',  'https://api.card-gorilla.com:8080/storage/card/739/card_img/22213/739card.png'], ['KB국민카드', '직장인보너스체크카드', '국세/지방세', '7,000원', 'GS칼텍스', '60~50원/L', '백화점', '5%',  'https://api.card-gorilla.com:8080/storage/card/350/card_img/20594/350card.png'], ['우체국', '개이득 체크카드', '국내 전 가맹점', '0.3%', 'OTT', '30%', '패션', '30%',  'https://api.card-gorilla.com:8080/storage/card/2519/card_img/28824/2519card.png'], ['NH농협카드', '라이언 치즈 체크카드', '커피', '최대 1%', '대중교통', '1.5%', '유튜브결제', '5%',  'https://api.card-gorilla.com:8080/storage/card/528/card_img/21216/528card.png'], ['현대카드', '현대카드M CHECK', '이용금액별', '1~0.5%', '', '', '', '',  'https://api.card-gorilla.com:8080/storage/card/407/card_img/28394/407card.png'], ['우리카드', 'NU Uniq Check', '국내 가맹점', '0.2%', 'DAILY 가맹점', '1%', 'EAT 가맹점', '1%',  'https://api.card-gorilla.com:8080/storage/card/2370/card_img/25307/2370card.png'], ['하나카드', '알뜰교통 비바 e 플래티늄 체크카드', '대중교통', '15%', '온라인쇼핑 최대', '5천원', '해외직구 배송대행료',  '20%',  'https://api.card-gorilla.com:8080/storage/card/746/card_img/27830/746card.png'], ['NH농협카드', '올리 POINT 체크', '국내외가맹점', '0.2%', '1~2위 이용영역', '2~3배', '공항라운지', '무료',  'https://api.card-gorilla.com:8080/storage/card/676/card_img/21441/676card.png'], ['우리카드', '카드의정석 UniMile CHECK', 'UniMile', '0.2~1.5%', '국내외', '공항라운지',  '해외 유명 박물관/미술관', '50%',  'https://api.card-gorilla.com:8080/storage/card/1809/card_img/22300/1809card.png'], ['트래블월렛', '트래블페이 충전카드', '주요 통화', '무료', '해외결제', '0%', '충전가능외화', '31개',  'https://api.card-gorilla.com:8080/storage/card/2320/card_img/23848/2320card.png'], ['KB국민카드', 'Young Youth 체크카드', '뷰티/편의점', '5%', '패스트푸드', '5%', '온라인쇼핑', '5%',  'https://api.card-gorilla.com:8080/storage/card/760/card_img/22234/760card.png'], ['KB국민카드', '펭수 노리 체크카드(펭카)', '대중교통', '10%', '이동통신요금', '2,500원', 'CGV', '35%',  'https://api.card-gorilla.com:8080/storage/card/559/card_img/21264/559card.png'], ['KB국민카드', '해피노리체크카드', '대중교통', '10%', '스타벅스', '20%', 'CGV', '35%',  'https://api.card-gorilla.com:8080/storage/card/342/card_img/20587/342card.png'], ['신한카드', '네이버페이 신한카드 체크', '전가맹점', '1%', '', '', '', '',  'https://api.card-gorilla.com:8080/storage/card/460/card_img/20981/460card.png'], ['신한카드', '신한카드 FANtastic S 체크(LoL)', '간편결제(신한플레이)', '15%', '쿠팡,티몬', '10%',  '스타벅스', '10%',  'https://api.card-gorilla.com:8080/storage/card/2426/card_img/27197/2426card.png'], ['IBK기업은행', 'IBK무민카드(체크)', '대중교통', '건당100원', '온라인쇼핑', '10%', '커피전문점', '10%',  'https://api.card-gorilla.com:8080/storage/card/674/card_img/21439/674card.png'], ['교보증권', 'Win.K 체크카드', '학원', '10%', '교보문고', '10%', 'SK주유소', '100원/L',  'https://api.card-gorilla.com:8080/storage/card/1900/card_img/22096/1900card.png'], ['NH농협카드', '올바른 NEW HAVE체크카드', '국내외가맹점', '0.2%', '이용금액1위', '3배', '국제공항라운지',  '무료',  'https://api.card-gorilla.com:8080/storage/card/1765/card_img/22348/1765card.png'], ['KB국민카드', '민 체크카드', '대형마트', '5%', 'SK에너지', '60원/L', '대중교통', '5%',  'https://api.card-gorilla.com:8080/storage/card/346/card_img/20591/346card.png'], ['하나카드', 'VIVA X 체크카드', '해외가맹점 수수료', '면제', '해외ATM 수수료', '면제', '국내가맹점', '0.3%',  'https://api.card-gorilla.com:8080/storage/card/749/card_img/22223/749card.png'], ['KB국민카드', '청춘대로 싱글 체크카드', '편의점', '10~5%', '다이소/소셜커머스', '10~5%', '동물병원', '10%',  'https://api.card-gorilla.com:8080/storage/card/335/card_img/20579/335card.png'], ['케이뱅크', '케이뱅크X네이버페이 체크카드2', '이용금액', '1.2%', 'ATM수수료', '무료', 'GS', 'POP',  'https://api.card-gorilla.com:8080/storage/card/437/card_img/20940/437card.png'], ['NH농협카드', '어피치 스윗 체크카드', '온라인 간편결제', '3%', '카페', '4%', '넷플릭스', '5%',  'https://api.card-gorilla.com:8080/storage/card/586/card_img/21342/586card.png'], ['신한카드', '신한 S20 체크카드', '버스/지하철/택시', '10%', '이동통신요금', '2~3천원', 'GS25', '2~7%',  'https://api.card-gorilla.com:8080/storage/card/298/card_img/20521/298card.png'], ['KB국민카드', 'ONE 체크카드', '모든가맹점', '0.2%', '주말/공휴일', '0.2%', '대중교통/택시', '0.2%',  'https://api.card-gorilla.com:8080/storage/card/337/card_img/20582/337card.png'], ['우리카드', 'LoL CHAMPIONS KOREA 우리체크카드', '해외가맹점', '1~2%', '국내업종별', '10~20%',  '인천공항라운지', '무료',  'https://api.card-gorilla.com:8080/storage/card/313/card_img/20558/313card.png'], ['우리카드', '그랑블루 체크카드', '모아포인트', '0.3~1.0%', '기프트바우처', '연1회', '공항라운지', '무료',  'https://api.card-gorilla.com:8080/storage/card/325/card_img/20570/325card.png'], ['신한카드', '신한카드 Pick I 체크', '편의점·커피', '10%', '디지털 구독', '10%', 'One Pick 쇼핑몰',  '3천 포인트',  'https://api.card-gorilla.com:8080/storage/card/2527/card_img/28967/2527card.png'], ['우체국', '행복한 체크카드', '마트', '10%', '병·의원/약국', '10%', '학원', '10%',  'https://api.card-gorilla.com:8080/storage/card/585/card_img/21340/585card.png'], ['MG새마을금고', '카카오페이 체크카드', '카카오페이', '15%', '편의점', '5%', '온라인쇼핑', '10%',  'https://api.card-gorilla.com:8080/storage/card/2094/card_img/21906/2094card.png'], ['IBK기업은행', 'IBK 나라사랑카드', '대중교통', '10%', '철도/고속버스', '5%', 'CU', '5%',  'https://api.card-gorilla.com:8080/storage/card/446/card_img/20952/446card.png'], ['케이뱅크', 'MY 체크카드', '편의점 최대', '500원', '카페 최대', '500원', '패스트푸드 최대', '500원',  'https://api.card-gorilla.com:8080/storage/card/2425/card_img/27182/2425card.png'], ['신한카드', '신한카드 4Tune 체크', '국내 모든 가맹점', '0.2%', '해외 모든 가맹점', '1%', '커피전문점',  '10%',  'https://api.card-gorilla.com:8080/storage/card/290/card_img/20513/290card.png'], ['IBK기업은행', 'IBK 무직타이거 카드(체크)', '버스,지하철', '건당 100원', '온라인쇼핑', '10%', '편의점',  '5%',  'https://api.card-gorilla.com:8080/storage/card/1490/card_img/20409/1490card.png'], ['KB국민카드', 'KB국민 우리동네 체크카드', '세탁소, 정육점', '10%', '슈퍼마켓', '5%', '번개장터', '5%',  'https://api.card-gorilla.com:8080/storage/card/2332/card_img/24588/2332card.png'], ['하나카드', 'VIVA+ 체크카드', '해외이용수수료', '0.5%', '해외인출수수료', '3%', '점심시간', '7%',  'https://api.card-gorilla.com:8080/storage/card/378/card_img/20878/378card.jpg'], ['KB국민카드', '해외에선 체크카드', '여행업종', '5%', '배달앱', '5%', '해외 물품 구매 수수료', '1.25%',  'https://api.card-gorilla.com:8080/storage/card/689/card_img/22162/689card.png'], ['NH농협카드', 'BAZIC(베이직)체크카드', '국내외 전 가맹점', '0.3~0.2%', '', '', '', '',  'https://api.card-gorilla.com:8080/storage/card/370/card_img/20867/370card.png'], ['삼성카드', '삼성체크카드 & CASHBACK', '0.6~0.2%', '캐시백', '놀이공원', '50%', 'CGV', '3,000원',  'https://api.card-gorilla.com:8080/storage/card/394/card_img/28057/394card.png'], ['신한카드', 'FANtastic S 신한카드 체크', '간편결제(FAN페이)', '15%', '쿠팡,티몬', '10%', '스타벅스',  '10%',  'https://api.card-gorilla.com:8080/storage/card/278/card_img/20500/278card.png'], ['우리카드', '카드의정석 POINT CHECK', '모아포인트', '1.5~0.3%', '간편결제', '1.5%',  '롯데월드 아쿠아리움/놀이공원', '50%',  'https://api.card-gorilla.com:8080/storage/card/317/card_img/20563/317card.png'], ['신한카드', '신한카드 Way 체크(최고심)', '대중교통 최대', '5%', '생활편의 최대', '2%', '해외 이용금액',  '1.3%',  'https://api.card-gorilla.com:8080/storage/card/2471/card_img/28241/2471card.png'], ['현대카드', '현대카드M HYBRID', '이용금액별', '1~0.5%', '', '', '', '',  'https://api.card-gorilla.com:8080/storage/card/414/card_img/28396/414card.png'], ['KB국민카드', '포인트리체크카드', 'GS칼텍스', '60~50점/L', 'CGV', '3,000원', '이동통신요금', '2,500점',  'https://api.card-gorilla.com:8080/storage/card/343/card_img/20588/343card.png'], ['DGB대구은행', '해피포인트 DGB체크카드', '해피포인트', '20%', '해피포인트', '5%', '이동통신요금', '3%',  'https://api.card-gorilla.com:8080/storage/card/814/card_img/21728/814card.png'], ['우리카드', '카카오페이 체크카드', '카카오페이 가맹점', '5%', '온라인쇼핑몰', '5%', '편의점/드럭스토어', '5%',  'https://api.card-gorilla.com:8080/storage/card/315/card_img/20560/315card.png'], ['KB증권', 'able Premier Members 카드', '한도없이', '0.5%', '호텔 최대', '50%', '', '',  'https://api.card-gorilla.com:8080/storage/card/2225/card_img/21356/2225card.png'], ['하나카드', '카카오페이 체크카드', '카카오페이', '7%', '대중교통', '5%', '스타벅스', '5%',  'https://api.card-gorilla.com:8080/storage/card/382/card_img/20882/382card.png'], ['카카오뱅크', '카카오뱅크 개인사업자 체크카드', '생활업종', '0.3%', '사업업종', '3%', '', '',  'https://api.card-gorilla.com:8080/storage/card/2421/card_img/27093/2421card.png'], ['신한카드', '신한카드 하이패스(전용) 체크', '하이패스요금', '0.1%', '통행료', '20%', '', '',  'https://api.card-gorilla.com:8080/storage/card/391/card_img/20890/391card.png'], ['KB국민카드', '쏘영 체크카드', '스트리밍', '5%', '패스트푸드', '5%', '편의점', '5%',  'https://api.card-gorilla.com:8080/storage/card/680/card_img/21445/680card.png'], ['KB국민카드', '총무 체크카드', '한식, 휴게음식점', '5%', '커피/제과', '5%', '대중교통', '5%',  'https://api.card-gorilla.com:8080/storage/card/2530/card_img/29047/2530card.png'], ['MG새마을금고', '꿀카드', '쿠팡 로켓와우 최대', '2,900원', 'OTT 서비스', '2천원', '음원 스트리밍', '1천원',  'https://api.card-gorilla.com:8080/storage/card/2001/card_img/21997/2001card.png'], ['우체국', '영리한 PLUS 체크카드', '디지털콘텐츠', '20%', '온라인쇼핑', '15%', '배달앱', '15%',  'https://api.card-gorilla.com:8080/storage/card/2227/card_img/21354/2227card.png'], ['케이뱅크', '케이뱅크 플러스 체크카드', '모든가맹점', '0.3%', 'ATM수수료', '무료', '언택트 업종', '3%',  'https://api.card-gorilla.com:8080/storage/card/669/card_img/21434/669card.png'], ['카카오뱅크', '카카오뱅크 mini카드', '전국ATM 수수료', '무료', 'CU에서는', '현금없이', '', '',  'https://api.card-gorilla.com:8080/storage/card/686/card_img/21451/686card.png'], ['신한카드', '신한카드 주거래 체크', '전가맹점', '0.3%', '쇼핑, 해외가맹점', '0.7%', '생활가맹점', '1.7%',  'https://api.card-gorilla.com:8080/storage/card/284/card_img/20506/284card.png'], ['신한카드', '신한카드 Deep Dream 체크(모베러웍스)', '모두드림', '0.2%', '더해드림', '0.6%', '챙겨드림',  '1.0%',  'https://api.card-gorilla.com:8080/storage/card/2391/card_img/25864/2391card.png'], ['교보증권', '부자되세요 더마일리지 체크카드', '대한항공 마일리지', '1000원당 1마일', '온라인몰', '5%',  '11-14시 음식점', '10%',  'https://api.card-gorilla.com:8080/storage/card/1903/card_img/22093/1903card.png'], ['토스뱅크', '토스뱅크 모임카드', '대형마트 최대', '500원', '노래방 최대', '500원', '음식점/주점 최대', '500원',  'https://api.card-gorilla.com:8080/storage/card/2456/card_img/28028/2456card.png'], ['카카오페이', '카카오페이카드3', 'MZ라이프팩', '최대 3만포인트', '어디서나적립팩', '0.3%', '', '',  'https://api.card-gorilla.com:8080/storage/card/2303/card_img/23334/2303card.png'], ['현대카드', '현대카드X CHECK', '사용금액별', '0.6~0.3%', '', '', '', '',  'https://api.card-gorilla.com:8080/storage/card/408/card_img/28395/408card.png'], ['NH농협카드', 'NH농협 올바른 하나로(Hanaro) 체크카드', '전 가맹점', '0.2%', '농협판매장', '0.5~1%',  '공항라운지', '무료',  'https://api.card-gorilla.com:8080/storage/card/532/card_img/21229/532card.png'], ['신한카드', '신한카드 플리(체크)', '국내 이용금액', '최대 0.3%', '해외 이용금액', '0.2%', '매 월 2회',  '할인쿠폰',  'https://api.card-gorilla.com:8080/storage/card/2372/card_img/25330/2372card.png'], ['우리카드', '카드의정석 CREAM TEENS CHECK', '편의점 건당', '1,000원', '서점 건당', '1,000원',  '베이커리/패스트푸드 건당', '1,000원',  'https://api.card-gorilla.com:8080/storage/card/330/card_img/20574/330card.png'], ['KB국민카드', '비트윈체크카드', '패밀리레스토랑', '15~10%', '영화', '20%', 'GS25', '5%',  'https://api.card-gorilla.com:8080/storage/card/347/card_img/20592/347card.png'], ['KB국민카드', '가온 올포인트 체크카드', '국내외 가맹점', '0.2%', '대형마트', '0.8%', 'GS칼텍스', '0.8%',  'https://api.card-gorilla.com:8080/storage/card/454/card_img/20970/454card.png'], ['KB국민카드', '펭수 노리 체크카드(펭모티콘)', '대중교통', '10%', '이동통신요금', '2,500원', 'CGV', '35%',  'https://api.card-gorilla.com:8080/storage/card/561/card_img/21266/561card.png']]
# , axis=0)
# print(data[0][2])

allkeyword = np.array(['0.6~0.2%','1~2위 이용영역','10회결제 때마다','DAILY 가맹점','KB Pay','UniMile','결제할 때마다','국내 가맹점','국내 결제시','국내 모든 가맹점','국내 어디서나','국내 이용금액','국내 전 가맹점','국내가맹점','국내업종별','네이버페이','네이버페이,카카오페이','더해드림','매 월 2회','매월 3번 응DAY엔','모두드림','모든가맹점','모아포인트','발급즉시','사용금액별','어디서나적립팩','이달의 브랜드','이용금액','이용금액별','전 가맹점','전가맹점','주말/공휴일','챙겨드림','카카오페이','카카오페이 가맹점','카카오페이 결제','한도없이', '해피포인트','사업업종', '생활업종'])
airkeyword = np.array(['공항라운지','국제공항라운지','대한항공 마일리지','인천공항라운지','전세계 공항라운지','진에어'])
edukeyword = np.array(['학원'])
trankeyword = np.array(['대중교통','대중교통 최대','대중교통/택시','버스/지하철/택시','전국 지하철/버스/택시', '철도/고속버스', '버스,지하철','통행료','하이패스요금'])
culkeyword = np.array(['MZ라이프팩','OTT','OTT 서비스','네이버 외','네이버 최대','노래방 최대','놀이공원','디지털 구독','디지털콘텐츠','롯데월드 아쿠아리움/놀이공원','사진·볼링·테니스·스키','생활가맹점','생활편의','생활편의 최대','서점 건당','소셜커머스','스트리밍','언택트 업종','영화','유튜브결제','음원 스트리밍','교보문고','CGV','CGV 최대	'])
delkeyword = np.array(['배달/주문','배달앱','배달의민족','카카오톡 주문하기','쿠팡 로켓와우 최대','쿠팡,티몬','쿠팡캐시','텐바이텐'])
storekeyword = np.array(['One Pick 쇼핑몰','백화점','현대백화점','현대아울렛'])
giftkeyword = np.array(['기프트바우처'])
foodkeyword = np.array(['11-14시 음식점','EAT 가맹점','One Pick 맛집','베이커리/패스트푸드 건당','음식점','음식점/주점 최대','패밀리레스토랑 최대','패스트푸드','패스트푸드 최대','점심시간', '패밀리레스토랑'])
shopkeyword = np.array(['간편결제','간편결제(FAN페이)','간편결제(신한플레이)','쇼핑, 해외가맹점','쇼핑/커피/영화 등','온라인 쇼핑','온라인몰','온라인쇼핑','온라인쇼핑 최대','온라인쇼핑몰','농협판매장','다이소/소셜커머스','대형마트','대형마트 최대','마트','번개장터','뷰티, 편의점','세탁소, 정육점','슈퍼마켓'])
postkeyword = np.array(['우체국'])
medikeyword = np.array(['동물병원','병·의원/약국','병원 업종','병의원'])
clokeyword = np.array(['패션'])
commikeyword = np.array(['ATM수수료','전 세계 ATM','전국ATM 수수료'])
travelkeyword = np.array(['여행업종'])
gaskeyword = np.array(['GS칼텍스','SK에너지','SK주유소'])
cafekeyword = np.array(['스타벅스','카페 최대','커피','커피, 모바일, 문화','커피전문점', '카페'])
commukeyword = np.array(['이동통신요금','이동통신요금 최대','휴대폰 소액결제로'])
convikeyword = np.array(['CU에서는','GS','GS25','GS리테일 점내','P.X / GS25해군마트 최대','편의점','편의점 건당','편의점 최대','편의점/드럭스토어','편의점·커피'])
overseakeyword = np.array(['국내외','국내외 가맹점','국내외 전 가맹점','국내외가맹점','국외가맹점','해외','해외 결제시','해외 모든 가맹점','해외 물품 구매 수수료','해외 유명 박물관/미술관','해외 이용금액','해외ATM 수수료','해외가맹점','해외가맹점 수수료','해외결제','해외수수료','해외이용수수료','해외인출수수료','해외직구 배송대행료'])
forekeyword = np.array(['주요 통화','충전가능외화'])
hotelkeyword = np.array(['호텔 최대'])
publkeyword = np.array(['국세/지방세'])


# 스크래핑 된 데이터의 Column을 정제한다.
def customFilter(data) :
    # 명시할 칼럼 = [[카드사 , 카드명, 혜택1, 혜택값1, 혜택2, 혜택값2, 혜택3, 혜택값3, 카드이미지]]의 9개
    refinedata = np.empty((0, 9), str)
    refinedata = np.append(refinedata, data, axis=0)

    # 몇개나 있니?
    datalength = len(data)

    # Column 목록 정제
    for x in range(0, datalength, 1):
        for y in range(2, 7, 2):
            benefit = data[x][y]
            for z in range(0, len(allkeyword), 1):
                if (allkeyword[z] in benefit) :
                    refinedata[x][y] = 'all'

            for z in range(0, len(airkeyword), 1):
                if (airkeyword[z] in benefit) :
                    refinedata[x][y] = 'airport'

            for z in range(0, len(edukeyword), 1):
                if (edukeyword[z] in benefit) :
                    refinedata[x][y] = 'edu'
                    
            for z in range(0, len(trankeyword), 1):
                if (trankeyword[z] in benefit) :
                    refinedata[x][y] = 'tran'

            for z in range(0, len(culkeyword), 1):
                if (culkeyword[z] in benefit) :
                    refinedata[x][y] = 'cultural'

            for z in range(0, len(delkeyword), 1):
                if (delkeyword[z] in benefit) :
                    refinedata[x][y] = 'delivery'

            for z in range(0, len(storekeyword), 1):
                if (storekeyword[z] in benefit) :
                    refinedata[x][y] = 'stores'

            for z in range(0, len(giftkeyword), 1):
                if (giftkeyword[z] in benefit) :
                    refinedata[x][y] = 'giftcard'

            for z in range(0, len(foodkeyword), 1):
                if (foodkeyword[z] in benefit) :
                    refinedata[x][y] = 'food'

            for z in range(0, len(shopkeyword), 1):
                if (shopkeyword[z] in benefit) :
                    refinedata[x][y] = 'shop'

            for z in range(0, len(postkeyword), 1):
                if (postkeyword[z] in benefit) :
                    refinedata[x][y] = 'post'

            for z in range(0, len(medikeyword), 1):
                if (medikeyword[z] in benefit) :
                    refinedata[x][y] = 'medi'

            for z in range(0, len(clokeyword), 1):
                if (clokeyword[z] in benefit) :
                    refinedata[x][y] = 'cloth'

            for z in range(0, len(commikeyword), 1):
                if (commikeyword[z] in benefit) :
                    refinedata[x][y] = 'commi'

            for z in range(0, len(travelkeyword), 1):
                if (travelkeyword[z] in benefit) :
                    refinedata[x][y] = 'travel'

            for z in range(0, len(gaskeyword), 1):
                if (gaskeyword[z] in benefit) :
                    refinedata[x][y] = 'gas'

            for z in range(0, len(cafekeyword), 1):
                if (cafekeyword[z] in benefit) :
                    refinedata[x][y] = 'coffee'

            for z in range(0, len(commukeyword), 1):
                if (commukeyword[z] in benefit) :
                    refinedata[x][y] = 'commu'

            for z in range(0, len(convikeyword), 1):
                if (convikeyword[z] in benefit) :
                    refinedata[x][y] = 'conveni'

            for z in range(0, len(overseakeyword), 1):
                if (overseakeyword[z] in benefit) :
                    refinedata[x][y] = 'overseas'

            for z in range(0, len(forekeyword), 1):
                if (forekeyword[z] in benefit) :
                    refinedata[x][y] = 'foreign'

            for z in range(0, len(hotelkeyword), 1):
                if (hotelkeyword[z] in benefit) :
                    refinedata[x][y] = 'hotel'

            for z in range(0, len(publkeyword), 1):
                if (publkeyword[z] in benefit) :
                    refinedata[x][y] = 'public'

            refinebenefit = refinedata[x][y]

    # Column 목록 값 정제
    for x in range(0, datalength, 1):
        for y in range(3, 8, 2):
            benefitval = data[x][y]

            refval = re.sub("천원", '@', benefitval)
            refval = re.sub("만", '#', refval)
            refval = re.sub("무료입장", '40000', refval)
                
            # 기타 예외처리 -- 라운지 가격 4만원 고정// 기름값 L당 1500 고정// 연1회 = 기프트 바우처// 면제 = 해외수수료로 1%// 수수료 = 기타 수수료 1% // 캐시백 = 카드 하나가 씀...
            refval = re.sub("공항라운지", '40000', refval)
            refval = re.sub("무료", '40000', refval)
            refval = re.sub("연1회", '10%', refval)
            refval = re.sub("L", "!", refval)
            refval = re.sub("면제", "1%", refval)
            refval = re.sub("수수료", "1%", refval)
            refval = re.sub("캐시백", "0.6~0.2%", refval)
            refval = re.sub("현금없이", "", refval)
            refval = re.sub("1000원당 1마일", "0.1%", refval)
            refval = re.sub("건당 100원", "0.0625%", refval)

            refval = re.sub(r"[^0-9.%~@#_!]", "", refval)

            # print(benefitval + ' -> ' + refval)



            if '~' in refval:
                # 함수만들기
                fil1 = refval.find('~')
                aaa = 1
                bbb = 1
                if (refval.find('%') == -1):
                    leng = len(refval)
                else:
                    leng = refval.find('%')

                if (refval.find('@') != -1):
                    leng = leng - 1
                    aaa = 1000
                if (refval.find('#') != -1):
                    leng = leng - 1
                    aaa = 10000
                if (refval.find('!') != -1):
                    leng = leng - 1
                    # 기름 평균가
                    bbb = 1500

                val1 = float(refval[0:fil1])
                val2 = float(refval[fil1 + 1 :leng])
                val3 = round((val1 + val2) / 2  / bbb , 2) * aaa
                refval = re.sub(refval[0:leng], str(val3), refval)

            else:
                refval = re.sub('@', '000', refval)
                refval = re.sub('#', '0000', refval)
                if '!' in refval:
                    refval = re.sub('!', "", refval)
                    refval = (str)(float(refval) / 1500)

            # print(benefitval + ' -> ' + refval)
            refval = re.sub(r"[@#!]", '', refval)
            refval = re.sub('_', 'free', refval)
            if '%' in refval:
                refval = re.sub('%', "", refval)
                refval = round(float(refval) / 100 , 4)
            finval = str(refval)
            # print(benefitval + ' -> ' + finval)
            refinedata[x][y] = finval
            
    # print(refinedata)

    return refinedata

customFilter(data)