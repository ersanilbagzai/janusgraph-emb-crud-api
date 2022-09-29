package com.techframer.janusgraph.api.crud.controller;

import com.techframer.janusgraph.api.crud.graph.wrapper.GraphEdge;
import com.techframer.janusgraph.api.crud.graph.wrapper.GraphVertex;
import com.techframer.janusgraph.api.crud.graph.exceptions.*;
import com.techframer.janusgraph.api.crud.graph.transaction.exceptions.TransactionException;
import io.swagger.annotations.Api;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@ResponseBody
@Api(tags = { "TechFramer Graph APIs" })
@SwaggerDefinition(tags = { @Tag(name = "TechFramer Graph APIs", description = "REST APIs for Graph Interaction") })
public interface GraphController {

	@GetMapping(path = "echo", produces = MediaType.TEXT_PLAIN_VALUE)
	ResponseEntity echo();

	@GetMapping(path = "v/id/{vId}", produces = MediaType.APPLICATION_JSON_VALUE)
	GraphVertex getVById(@PathVariable(name = "vId") String vertexId,
						 @RequestParam(required = false, name = "transId") String tId);
	
	@GetMapping(path = "v/gid/{gId}", produces = MediaType.APPLICATION_JSON_VALUE)
	GraphVertex getVByUUID(@PathVariable(name = "gId") String vertexId,
						   @RequestParam(required = false, name = "transId") String tId)
			throws GraphObjectNotExistsException, TransactionException, GraphUnmarshallingException, GraphServiceException;

	@PostMapping(path = "v/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	GraphVertex addV(@RequestBody(required = false) GraphVertex vertex,
					 @RequestParam(required = false, name = "transId") String tId)
			throws GraphObjectNotExistsException, GraphMarshallingException, TransactionException;

	@PatchMapping(path = "v/patch/id", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	GraphVertex patchVByVId(@RequestBody(required = false) GraphVertex champObj,
							@RequestParam(required = false, name = "transId") String tId) throws
			GraphObjectNotExistsException, TransactionException, GraphMarshallingException, GraphUnmarshallingException, GraphServiceException;
	
	@PatchMapping(path = "v/patch/gid", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	GraphVertex patchVByUUID(@RequestBody(required = false) GraphVertex champObj,
							 @RequestParam(required = false, name = "transId") String tId) throws
			GraphObjectNotExistsException, TransactionException, GraphMarshallingException, GraphUnmarshallingException, GraphServiceException;

	@PutMapping(path = "v/replace/id", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	GraphVertex replaceVByVId(@RequestBody(required = false) GraphVertex champObj,
							  @RequestParam(required = false, name = "transId") String tId) throws GraphServiceException,
			GraphObjectNotExistsException, TransactionException, GraphMarshallingException, GraphUnmarshallingException;
	
	@PutMapping(path = "v/replace/gid", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	GraphVertex replaceVByUUID(@RequestBody(required = false) GraphVertex champObj,
							   @RequestParam(required = false, name = "transId") String tId) throws GraphServiceException,
			GraphObjectNotExistsException, TransactionException, GraphMarshallingException, GraphUnmarshallingException;

	@DeleteMapping(path = "v/drop/id/{vId}")
	Boolean dropVById(@PathVariable(name = "vId") String vertexId,
					  @RequestParam(required = false, name = "transId") String tId) throws GraphServiceException,
			GraphObjectNotExistsException, TransactionException, GraphUnmarshallingException;
	
	@DeleteMapping(path = "v/drop/gid/{gId}")
	Boolean dropVByUUID(@PathVariable(name = "gId") String vertexId,
						@RequestParam(required = false, name = "transId") String tId) throws GraphServiceException,
			GraphObjectNotExistsException, TransactionException, GraphUnmarshallingException;

	@PostMapping(path = "e/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	GraphEdge addE(@RequestBody(required = false) GraphEdge edge,
				   @RequestParam(required = false, name = "transId") String tId)
			throws GraphObjectNotExistsException, TransactionException, GraphUnmarshallingException,
			GraphEdgeNotExistsException, GraphMarshallingException, GraphServiceException;

	@PatchMapping(path = "e/patch/id", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	GraphEdge patchEById(@RequestBody(required = false) GraphEdge edge,
						 @RequestParam(required = false, name = "transId") String tId) throws GraphEdgeNotExistsException,
			GraphMarshallingException, GraphUnmarshallingException, TransactionException, GraphServiceException;
	
	@PatchMapping(path = "e/patch/gid", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	GraphEdge patchEByUUID(@RequestBody(required = false) GraphEdge edge,
						   @RequestParam(required = false, name = "transId") String tId) throws GraphEdgeNotExistsException,
			GraphMarshallingException, GraphUnmarshallingException, TransactionException, GraphServiceException;

	@PutMapping(path = "e/replace/id", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	GraphEdge replaceEById(@RequestBody(required = false) GraphEdge edge,
						   @RequestParam(required = false, name = "transId") String tId) throws GraphEdgeNotExistsException,
			GraphMarshallingException, GraphUnmarshallingException, TransactionException, GraphServiceException;
	
	@PutMapping(path = "e/replace/gid", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	GraphEdge replaceEByUUID(@RequestBody(required = false) GraphEdge edge,
							 @RequestParam(required = false, name = "transId") String tId) throws GraphEdgeNotExistsException,
			GraphMarshallingException, GraphUnmarshallingException, TransactionException, GraphServiceException;

	@DeleteMapping(path = "e/drop/id/{eId}")
	Boolean dropEById(@PathVariable(name = "eId") String edgeId,
					  @RequestParam(required = false, name = "transId") String tId)
			throws GraphEdgeNotExistsException, TransactionException, GraphServiceException ;
	
	@DeleteMapping(path = "e/drop/gid/{eGId}")
	Boolean dropEByUUID(@PathVariable(name = "eGId") String edgeId,
						@RequestParam(required = false, name = "transId") String tId)
			throws GraphEdgeNotExistsException, TransactionException, GraphServiceException ;

	@GetMapping(path = "e/id/{eId}", produces = MediaType.APPLICATION_JSON_VALUE)
	GraphEdge getEById(@PathVariable(name = "eId") String edgeId,
					   @RequestParam(required = false, name = "transId") String tId) throws GraphObjectNotExistsException,
			GraphUnmarshallingException, TransactionException, GraphEdgeNotExistsException;
	
	@GetMapping(path = "e/gid/{eGId}", produces = MediaType.APPLICATION_JSON_VALUE)
	GraphEdge getEByUUID(@PathVariable(name = "eGId") String edgeId,
						 @RequestParam(required = false, name = "transId") String tId) throws GraphObjectNotExistsException,
			GraphUnmarshallingException, TransactionException, GraphEdgeNotExistsException;

}
