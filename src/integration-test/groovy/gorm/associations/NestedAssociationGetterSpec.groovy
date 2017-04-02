package gorm.associations


import grails.test.mixin.integration.Integration
import grails.transaction.*
import spock.lang.*

@Integration
@Rollback
class NestedAssociationGetterSpec extends Specification {

    def setup() {
    	def containerType = new ContainerType(name: 'nginx')
    	containerType.save(flush:true)
    	def containerTypeSet = new ContainerTypeSet(name: 'nginx.single',category:'web',code:'layout.nginx.set',containerType:containerType)
    	containerTypeSet.save(flush:true)

    	def instanceTypeLayout = new InstanceTypeLayout(name: 'Nginx Single Docker Instance', code: 'nginx')
    	instanceTypeLayout.addToContainers(containerTypeSet)
    	instanceTypeLayout.save(flush:true)

    	def container = new Container(name: "container_1",containerType: containerType, containerTypeSet: containerTypeSet)

    	def instance = new Instance(name: 'Davids Test Instance', layout: instanceTypeLayout)
    	instance.addToContainers(container)
    	instance.save(flush:true)
    }


    /**
    * This one is the test failing in GORM 6.1.0.RC1 + all the way to 6.1.0.RELEASE
    * The association that breaks this failure is located in {@link Container} where it references {@link ContainerTypeSet}
    */
    void "should be able to query for container type on a set after a fetch has been made for all containers on an instance"() {
    	given:
    		def types
    	when:
            def instance = Instance.findByName('Davids Test Instance')
            //NOTE: If the containers association is loaded first, then the containerType can no longer be fetched via layout.containers.containerType. ALso of note is instance.containers is of type Collection<Container> where as instance.layout.containers is of type <ContainerTypeSet>

            //NOTE: This works fine until a direct association is added to containerTypeSet from Container
            instance.containers.collect{it.name}
            types = instance.layout.containers.collect{it.containerType}
    	then:
    		types[0].name == 'nginx'
    }

    void "should be able to fetch a contaienr type via the layout association on an instance"() {
        given:
            def types
        when:
            def instance = Instance.findByName('Davids Test Instance')
            types = instance.layout.containers.collect{it.containerType}
        then:
            types[0].name == 'nginx'
    }

    def cleanup() {
    	Instance.list()*.delete(flush:true)
    	InstanceTypeLayout.list()*.delete(flush:true)
    	Container.list()*.delete(flush:true)
    	ContainerTypeSet.list()*.delete(flush:true)
    	ContainerType.list()*.delete(flush:true)
    }

 
}
