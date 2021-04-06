var chatService = (function() {
	
	function getMyChatRooms(id, callback, error) {
		console.log("show mt rooms !");
		$.getJSON("/board002/chat/myChatRoom/" + id +".json", function(data) {
			if(data){
				callback(data);
			}
		}).fail(function(xhr, status, err) {
			if(error)
				error();
		});
		
	}
	
	function displayTime(timeValue) {		
		let dateObj = new Date(timeValue);
		let yy = dateObj.getFullYear();
		let mm = dateObj.getMonth()+1;
		let dd = dateObj.getDate();
		let hh = dateObj.getHours();
		let mi = dateObj.getMinutes();
		
		mm = mm < 10 ? '0' + mm : mm;
		dd = dd < 10 ? '0' + dd : dd;
		hh = hh < 10 ? '0' + hh : hh;
		mi = mi < 10 ? '0' + mi : mi;
		return yy + '년 ' + mm + '월 ' + dd + '일' + hh+'시 '+ mi+'분 ';
	}
	return {
		getMyChatRooms : getMyChatRooms,
		displayTime : displayTime
		
	};
})();