package gorm.associations

class Instance {

	String name
	InstanceTypeLayout layout

	static hasMany = [containers: Container]

	
    static constraints = {
    }
}
