package gorm.associations

class TestController {

    def index() { }


    def show() {
    	def instance = Instance.findByName('Davids Test Instance')
		instance.containers.collect{it.name}
		render text: instance.layout.containers.collect{it.containerType}[0]?.name
    }


    def setup() {
    	def containerType = new ContainerType(name: 'nginx')
    	containerType.save(flush:true)
    	def containerTypeSet = new ContainerTypeSet(name: 'nginx.single',category:'web',code:'layout.nginx.set',containerType:containerType)
    	containerTypeSet.save(flush:true)

    	def instanceTypeLayout = new InstanceTypeLayout(name: 'Nginx Single Docker Instance', code: 'nginx')
    	instanceTypeLayout.addToContainers(containerTypeSet)
    	instanceTypeLayout.save(flush:true)

    	def container = new Container(name: "container_1",containerType: containerType)

    	def instance = new Instance(name: 'Davids Test Instance', layout: instanceTypeLayout)
    	instance.addToContainers(container)
    	instance.save(flush:true)

    	render(text: 'SUCCESS')
    }
}
