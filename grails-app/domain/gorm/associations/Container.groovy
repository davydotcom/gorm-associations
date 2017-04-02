package gorm.associations

class Container {
	static belongsTo = [instance:Instance]

	String name

	ContainerType containerType

	//CULPRIT: adding this singular association breaks the test
	ContainerTypeSet containerTypeSet

    static constraints = {
    }
}
