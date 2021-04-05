var chatService = (function() {
	
	function getMyChatRooms(id, callback, error) {
		console.log("show mt rooms !");
		$.getJSON("/board002/chat/myChatRoom/" + id +".json", function(data) {
			if(data){
				callback(data);
				console.log(data);
			}
		}).fail(function(xhr, status, err) {
			if(error)
				error();
		});
		
	}
	return {
		getMyChatRooms : getMyChatRooms
	};
})();