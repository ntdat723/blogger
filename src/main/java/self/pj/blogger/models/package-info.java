@GenericGenerators({
        @GenericGenerator(
                name = "post_id_gen",
                strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
                parameters = {
                        @Parameter(
                                name = "sequence_name",
                                value = "post_seq"
                        ),
                        @Parameter(
                                name = "initial_value",
                                value = "1000"
                        ),
                        @Parameter(
                                name = "increment_size",
                                value = "1"
                        )
                }
        ),
        @GenericGenerator(
                name = "user_id_gen",
                strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
                parameters = {
                        @Parameter(
                                name = "sequence_name",
                                value = "user_seq"
                        ),
                        @Parameter(
                                name = "initial_value",
                                value = "1"
                        ),
                        @Parameter(
                                name = "increment_size",
                                value = "1"
                        )
                }
        ),
        @GenericGenerator(
                name = "role_id_gen",
                strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
                parameters = {
                        @Parameter(
                                name = "sequence_name",
                                value = "role_seq"
                        ),
                        @Parameter(
                                name = "initial_value",
                                value = "1"
                        ),
                        @Parameter(
                                name = "increment_size",
                                value = "1"
                        )
                }
        ),
        @GenericGenerator(
                name = "auth_id_gen",
                strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
                parameters = {
                        @Parameter(
                                name = "sequence_name",
                                value = "auth_seq"
                        ),
                        @Parameter(
                                name = "initial_value",
                                value = "1"
                        ),
                        @Parameter(
                                name = "increment_size",
                                value = "1"
                        )
                }
        )
})
package self.pj.blogger.models;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.GenericGenerators;
import org.hibernate.annotations.Parameter;