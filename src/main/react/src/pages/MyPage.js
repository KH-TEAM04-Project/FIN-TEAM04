import React, {useState, useEffect} from "react";
import axios from 'axios';
import {useNavigate} from "react-router-dom";
import ImageUpload from '../sections/auth/MyPage/imgUpload';
import Nav from '../layouts/dashboard';
import '../sections/auth/MyPage/MyPage.css'

function MyPage() {

    const [avatarSrc, setAvatarSrc] = useState('/assets/images/avatars/avatar_default.jpg');
    const [userData, setUserData] = useState({
        mname: "",
        mid: "",
        regno: "", // Resident Registration Number
        email: "",
        address: "",
        detailAddress: "",
        ph: "",
        pwd: "" // Include pwd in the initial state
    });
    const navigate = useNavigate();
    const [mno, setMno] = useState(""); // 토큰에서 추출한 sub 값 상태
    const [isEditing, setIsEditing] = useState(false); // 회원 정보 수정 여부 상태
    const [isChangingPassword, setIsChangingPassword] = useState(false); // 비밀번호 수정 여부 상태
    const [pwd, setpwd] = useState(""); // 현재 비밀번호 상태
    const [changePwd, setchangePwd] = useState(""); // 변경할 비밀번호 상태
    const [confirmPassword, setConfirmPassword] = useState(""); // 변경할 비밀번호 확인 상태
    const passwordRegex = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$/;

    // 로컬 스토리지에서 토큰 값을 가져옴
    const token = localStorage.getItem('accessToken');

    useEffect(() => {
        if (token) {
            // 토큰을 디코딩하여 payload 부분을 추출하고 JSON 파싱
            const decodedToken = JSON.parse(atob(token.split('.')[1]));

            axios.post("/member/MyPageCont", null, {
                headers: {
                    // http 헤더의 auth 부분에 accessToken 값 설정
                    'Authorization': `Bearer ${token}`
                }
            })
                .then(response => {
                    // 사용자 데이터를 성공적으로 가져온 경우
                    const userData = response.data;

                    // 사용자 정보를 상태에 설정
                    setUserData(userData);
                })
                .catch(error => {
                    // API 호출 중 에러 발생한 경우
                    console.error(error);
                });
        }
    }, [token]);

    // Function to display only the first six digits of the Resident Registration Number
    const getMaskedRegNo = () => {
        const {regno} = userData;
        if (regno.length >= 6) {
            return regno.slice(0, 6);
        }
        return regno;
    };

    // Function to handle updating the user information
    const handleUpdateMember = (e) => {
        e.preventDefault();

        // Implement the logic to handle updating user information
        const {email, detailAddress, address, ph} = userData;

        // Create a MemberReqDTO object with the updated information
        const memberReqDTO = {
            mno, // Include the mno value
            email,
            detailAddress,
            address,
            ph
        };

        // Send the updated user information to the backend
        axios.post("/member/memberUpdate", memberReqDTO, {
            headers: {
                // http 헤더의 auth 부분에 accessToken 값 설정
                'Authorization': `Bearer ${token}`
            }
        })
            .then(response => {
                // Successful update
                const updatedUserData = response.data;

                if (updatedUserData) {
                    // Update the user data in the state
                    setUserData(updatedUserData);
                    setIsEditing(false); // Exit editing mode
                    console.log("User information updated successfully");
                    alert("사용자 정보가 성공적으로 업데이트되었습니다.");
                } else {
                    console.log("Failed to update user information");
                    alert("사용자 정보 업데이트에 실패했습니다.");
                }
            })
            .catch(error => {
                // Error occurred while updating
                console.error(error);
                alert("사용자 정보 업데이트 중 에러가 발생했습니다.");
            });
    };

    // Function to handle updating the password
    const handleChangePassword = (e) => {
        e.preventDefault();

        // Implement the logic to handle changing password
        if (changePwd !== confirmPassword) {
            alert("변경할 비밀번호가 일치하지 않습니다.");
            return;
        }
        if (changePwd === pwd) {
            alert("현재 비밀번호와 새로운 비밀번호가 동일합니다. 다른 비밀번호를 입력해주세요.");
            return;
        }
        const passwordRegex = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$/;
        if (!passwordRegex.test(changePwd)) {
            alert(
                "비밀번호는 8자 이상의 영문자, 숫자, 특수문자(@$!%*#?&)를 최소 한 개씩 포함해야 합니다."
            );
            return;
        }
        const passwordReqDTO = {
            mno, // Include the mno value
            pwd,
            changePwd
        };

        // Send the password update request to the backend
        axios
            .post("/member/changePassword", passwordReqDTO, {
                headers: {
                    // http 헤더의 auth 부분에 accessToken 값 설정
                    'Authorization': `Bearer ${token}`
                }
            })
            .then((response) => {
                // Successful password change
                const result = response.data;

                if (result) {
                    // Password changed successfully
                    setpwd("");
                    setchangePwd("");
                    setConfirmPassword("");
                    setIsChangingPassword(false);
                    console.log("Password changed successfully");
                    alert("비밀번호가 성공적으로 변경되었습니다.");
                } else {
                    // Failed to change password
                    console.log("Failed to change password");
                    alert("비밀번호 변경에 실패했습니다.");
                }
            })
            .catch((error) => {
                // Error occurred while changing password
                console.error(error);
                alert("비밀번호 변경 중 에러가 발생했습니다.");
            });
    };

    // Function to handle changes in the input fields
    const handleChange = (field, value) => {
        setUserData(prevState => ({
            ...prevState,
            [field]: value
        }));
    };

    // 회원 삭제 함수
    const handleDelete = () => {
        // Display a confirmation prompt before deleting the member
        const confirmDelete = window.confirm("정말 회원을 삭제하시겠습니까?");

        if (confirmDelete) {
            // Send the mno value to the backend
            axios.delete("/member/memberDelete", {
                headers: {
                    // http 헤더의 auth 부분에 accessToken 값 설정
                    'Authorization': `Bearer ${token}`
                }
            })
                .then((response) => {
                    // Successful deletion
                    setUserData({
                        mname: "",
                        mid: "",
                        regno: "",
                        email: "",
                        address: "",
                        detailAddress: "",
                        ph: "",
                        pwd: "", // Include pwd in the initial state
                    });
                    alert("회원 정보가 삭제되었습니다.");

                    // Clear the access token from localStorage
                    localStorage.removeItem("accessToken");

                    navigate("/login");
                })
                .catch((error) => {
                    // Error occurred while deleting
                    console.error(error);
                });
        }
    };



    const handleGoBack = () => {
        navigate(-1); // Go back one step in the browser history
    };


    return (

        <div className={"center-container"}><Nav/> {/* 사이드바 컴포넌트를 추가 */}
            <div className={"content"}>
                <h1>마이페이지</h1>
                <ImageUpload avatarSrc={avatarSrc} setAvatarSrc={setAvatarSrc}/>

                {userData.mname && !isEditing && !isChangingPassword && (
                    <>
                        <p>이름: {userData.mname}</p>
                        <p>아이디: {userData.mid}</p>
                        <p>생년월일: {getMaskedRegNo()}</p>
                        <p>이메일: {userData.email}</p>
                        <p>주소: {userData.address}</p>
                        <p>상세주소: {userData.detailAddress}</p>
                        <p>휴대폰번호: {userData.ph}</p>
                    </>
                )}

                {isEditing && !isChangingPassword && (
                    <form>
                        <div>
                            <label htmlFor="email">이메일</label>
                            <input
                                type="text"
                                id="email"
                                value={userData.email}
                                onChange={(e) => handleChange("email", e.target.value)}
                            />
                        </div>
                        <div>
                            <label htmlFor="address">주소</label>
                            <input
                                type="text"
                                id="address"
                                value={userData.address}
                                onChange={(e) => handleChange("address", e.target.value)}
                            />
                        </div>
                        <div>
                            <label htmlFor="detailAddress">상세주소</label>
                            <input
                                type="text"
                                id="detailAddress"
                                value={userData.detailAddress}
                                onChange={(e) => handleChange("detailAddress", e.target.value)}
                            />
                        </div>
                        <div>
                            <label htmlFor="ph">휴대폰번호</label>
                            <input
                                type="text"
                                id="ph"
                                value={userData.ph}
                                onChange={(e) => handleChange("ph", e.target.value)}
                            />
                        </div>
                        <button onClick={handleUpdateMember}>회원 정보 수정</button>
                    </form>
                )}

                {isChangingPassword && (
                    <form>
                        <div>
                            <label htmlFor="pwd">현재 비밀번호</label>
                            <input
                                type="password"
                                id="pwd"
                                value={pwd}
                                onChange={(e) => setpwd(e.target.value)}
                            />
                        </div>
                        <div>
                            <label htmlFor="changePwd">변경할 비밀번호</label>
                            <input
                                type="password"
                                id="changePwd"
                                value={changePwd}
                                onChange={(e) => setchangePwd(e.target.value)}
                            />
                        </div>
                        <div>
                            <label htmlFor="confirmPassword">변경할 비밀번호 확인</label>
                            <input
                                type="password"
                                id="confirmPassword"
                                value={confirmPassword}
                                onChange={(e) => setConfirmPassword(e.target.value)}
                            />
                        </div>
                        {changePwd && !passwordRegex.test(changePwd) && (
                            <p className="password-requirements">
                                비밀번호는 8자 이상의 영문자, 숫자, 특수문자(@$!%*#?&)를 최소 한 개씩 포함해야 합니다.
                            </p>
                        )}
                        {changePwd !== confirmPassword && (
                            <p className="password-mismatch">
                                비밀번호 확인이 일치하지 않습니다.
                            </p>
                        )}
                        <button onClick={handleChangePassword}>비밀번호 변경</button>
                    </form>
                )}

                {userData.mname && !isEditing && !isChangingPassword && (
                    <>
                        <button onClick={() => setIsEditing(true)}>회원 정보 수정</button>
                        <button onClick={() => setIsChangingPassword(true)}>비밀번호 수정</button>
                        <button onClick={handleDelete}>회원 삭제</button>

                    </>
                )}
                <button onClick={handleGoBack}>뒤로 가기</button>
            </div>
        </div>
    );
}

export default MyPage;
