/**
The encoding API should be used by the transport modules that need to externalise the encoding behaviour of a body
element. The use of this encoding API is optional. The encoding API enables the transport layer to share and reuse
any encoding module that complies with this API. However, a transport layer can implement the encoding behaviour in
an internal way without this API. NOTE – Two types of encoding module can be implemented: a) A generic encoding
module implements the MALEncoder and MALDecoder interfaces in order to encode and decode body elements in a generic
way, i.e., by calling the generic methods ‘encode’ and ‘decode’ implemented by each specific structures. b) A
specific encoding module does not implement the MALEncoder and MALDecoder interfaces. It encodes and decodes body
elements in a specific way, e.g. by calling the getter and setter methods provided by the specific structures.
 */
package org.ccsds.moims.mo.mal.encoding;
