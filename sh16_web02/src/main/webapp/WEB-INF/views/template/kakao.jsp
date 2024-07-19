<%--
/**
	Class Name: 
	Description:
	Author: acorn
	Modification information
	
	수정일     수정자      수정내용
    -----   -----  -------------------------------------------
    2024. 7. 18        최초작성 
    
    DOMA 개발팀
    since 2020.11.23
    Copyright (C) by KandJang All right reserved.
*/
 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <%-- favicon --%>
    <link rel="shortcut icon" href="/ehr/resources/img/favicon.ico" type="image/x-icon">
    
    <%-- bootstrap css --%>
    <link rel="stylesheet" href="/ehr/resources/css/bootstrap.css">
    
    <%-- jquery js --%>
    <script src="/ehr/resources/js/jquery_3_7_1.js"></script>
    
    <%-- bootstrap js --%>
    <script src="/ehr/resources/js/bootstrap.min.js"></script>
    
    <%-- common js --%>
    <script src="/ehr/resources/js/common.js"></script>
    
    <title>Kakao 지도 시작하기</title>
</head>
<body>
    <div id="map" style="width:800px;height:700px;"></div>
    <script type="text/javascript" src="https://dapi.kakao.com/v2/maps/sdk.js?appkey=697612f7482b0b832f526a2e125de900"></script>
    <script>
        window.onload = function() {
            var container = document.getElementById('map');
            var options = {
                center: new kakao.maps.LatLng(33.450701, 126.570667),
                level: 4
            };
            var map = new kakao.maps.Map(container, options);
            
            // 교통 혼잡도 표시
            map.addOverlayMapTypeId(kakao.maps.MapTypeId.TRAFFIC); 
            
            
            // 다각형을 구성하는 좌표 배열입니다. 이 좌표들을 이어서 다각형을 표시합니다
            var polygonPath = [
                new kakao.maps.LatLng(33.45133510810506, 126.57159381623066),
                new kakao.maps.LatLng(33.44955812811862, 126.5713551811832),
                new kakao.maps.LatLng(33.449986291544086, 126.57263296172184),
                new kakao.maps.LatLng(33.450682513554554, 126.57321034054742),
                new kakao.maps.LatLng(33.451346760004206, 126.57235740081413) 
            ];

            // 지도에 표시할 다각형을 생성합니다
            var polygon = new kakao.maps.Polygon({
                path:polygonPath, // 그려질 다각형의 좌표 배열입니다
                strokeWeight: 3, // 선의 두께입니다
                strokeColor: '#39DE2A', // 선의 색깔입니다
                strokeOpacity: 0.8, // 선의 불투명도 입니다 1에서 0 사이의 값이며 0에 가까울수록 투명합니다
                strokeStyle: 'longdash', // 선의 스타일입니다
                fillColor: '#A2FF99', // 채우기 색깔입니다
                fillOpacity: 0.7 // 채우기 불투명도 입니다
            });

            // 지도에 다각형을 표시합니다
            polygon.setMap(map);
        };
    </script>
</body>
</html>