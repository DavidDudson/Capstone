define([ './bruyere-commons', './bruyere-settings' ], function(commons,settings) {
	
	var Bot = function(_metadata, _src) {
		this.src = _src;
		this.metadata = _metadata;
		this.dirty = false;
	}

	Bot.prototype.setSrc = function(src) {
		this.src = src;
	};
	Bot.prototype.getSrc = function() {
		return this.src;
	};
	Bot.prototype.getId = function() {
		return this.metadata ? this.metadata.id : null;
	};
	Bot.prototype.getName = function() {
		return this.metadata ? this.metadata.name : null;
	};
	Bot.prototype.getLanguage = function() {
		return this.metadata ? this.metadata.language : null;
	};
	Bot.prototype.setMetadata = function(metadata) {
		this.metadata = metadata;
	};
	Bot.prototype.getMetadata = function() {
		return this.metadata;
	};
	Bot.prototype.isPersistent = function() {
		return !!this.getId(); 
	};

	Bot.prototype.getBuildURL = function() {
		var url = settings.root + "bots"
		if (this.getId()) {
			url = url + "/" + this.getId();
		}
		return url;
	};
	Bot.prototype.getDeleteURL = function() {
		return settings.root + "delete" + "/" + this.getId();
	};
	Bot.prototype.getSrcURL = function() {
		return settings.root + "bots-src/" + this.getId();
	};
	Bot.prototype.changed = function(flag) {
		this.dirty = flag;
	};
	Bot.prototype.saved = function() {
		this.dirty = false;
	};
	Bot.prototype.hasUnsavedChanges = function() {
		return !this.isPersistent() || this.dirty;
	};
	Bot.prototype.isShared = function() {
		return this.metadata ? this.metadata.shared === "true" : null;
	};
	Bot.prototype.setShared = function(flag) {
		this.metadata.shared = flag;
	};
	return Bot;
});