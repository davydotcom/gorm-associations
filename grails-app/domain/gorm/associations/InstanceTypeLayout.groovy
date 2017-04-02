package gorm.associations

class InstanceTypeLayout {

	String name
	String code

	static hasMany=[containers:ContainerTypeSet]
    static constraints = {
    }
}
